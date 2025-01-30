package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Item;
import com.mycompany.myapp.domain.RefType;
import com.mycompany.myapp.domain.RefType.RefTo;
import com.mycompany.myapp.feign.CategoryClient;
import com.mycompany.myapp.feign.ItemClient;
import com.mycompany.myapp.repository.ItemRepository;
import com.mycompany.myapp.service.ItemService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.server.ObjID;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Item}.
 */
@RestController
@RequestMapping("/api")
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    private static final String ENTITY_NAME = "item";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemService itemService;

    private final ItemRepository itemRepository;

    private final ItemClient itemClient;

    private final CategoryClient categoryClient;

   

    public ItemResource(ItemService itemService, ItemRepository itemRepository, ItemClient itemClient,CategoryClient categoryClient) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.itemClient = itemClient;
        this.categoryClient = categoryClient;
    }

    @PostMapping("cat/{catId}/item")
    public ResponseEntity<?>  additembycat(@PathVariable String catId , @RequestBody Item item){
        return itemService.savebyid(catId  , item);
    }

    /**
     * {@code POST  /items} : Create a new item.
     *
     * @param item the item to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new item, or with status {@code 400 (Bad Request)} if the item has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pro5/item")
    public ResponseEntity<?> createItem(@RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        item.setId(new ObjectId().toHexString());
        return itemClient.save(item);
    }


    // Update an existing item
    @PutMapping("/pro5/item/{id}")
    public ResponseEntity<?> updateItem(@PathVariable("id") String id, @RequestBody Item item)
            throws URISyntaxException {
        log.debug("REST request to update Item : {}", item);

        if (!id.equals(item.getId())) {
            throw new BadRequestAlertException("ID in URL and request body must match", "Comment", "idnotmatch");
        }

        ResponseEntity<Void> updateComment = itemClient.update(id, item);
        return updateComment;
    }

    
    @PatchMapping(value = "/items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Item> partialUpdateItem(@PathVariable(value = "id", required = false) final String id, @RequestBody Item item)
        throws URISyntaxException {
        log.debug("REST request to partial update Item partially : {}, {}", id, item);
        if (item.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, item.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Item> result = itemService.partialUpdate(item);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, item.getId()));
    }

    /**
     * {@code GET  /items} : get all the items.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of items in body.
     */
    @GetMapping("/pro5/item")
    public ResponseEntity<List<Item>> getAllItems() {
        log.debug("REST request to get all Items");
        ResponseEntity<List<Item>> item = itemClient.findAll();
        return item;
    }

    /**
     * {@code GET  /items/:id} : get the "id" item.
     *
     * @param id the id of the item to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the item, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("pro5/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id) {
        log.debug("REST request to get Item : {}", id);
        ResponseEntity<Item> item = itemClient.getById(id);
        return item;
    }

    // Delete a category by ID
   @DeleteMapping("/pro5/item/{id}")
   public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
       log.debug("REST request to delete Item : {}", id);

       ResponseEntity<Void> deleteItem = itemClient.delete(id);
       return deleteItem;
   }

   //add item to a specific category
    @PostMapping("/pro5/item/{categoryId}/category")
    public ResponseEntity<?> addItemToCategory(@PathVariable("categoryId") String categoryID, @RequestBody Item item) 
    throws URISyntaxException{
      log.debug("REST request to add item : {} to Post : {}",item,categoryID);

      item.setId(new ObjectId().toHexString());
      item.setCategory(new RefType(new ObjectId().toHexString(),RefTo.category));

      ResponseEntity<Void> addItemToCategory = itemClient.save(item);
      String location = addItemToCategory.getHeaders().get("Location").get(0);
      String itemid = location.substring(location.lastIndexOf("/") + 1);

      ResponseEntity<Category> byId = categoryClient.getById(categoryID);
      Category category = byId.getBody();
      category.getItem().add(new RefType(new ObjectId(itemid).toHexString(), RefTo.item));
      categoryClient.update(categoryID,category);


      return addItemToCategory;
    }
}
