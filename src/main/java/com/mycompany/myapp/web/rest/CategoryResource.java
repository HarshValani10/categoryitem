package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.feign.CategoryClient;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Category}.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "category";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    private final CategoryClient categoryClient;

   
    public CategoryResource(CategoryService categoryService, CategoryRepository categoryRepository,
            CategoryClient categoryClient) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.categoryClient = categoryClient;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param category the category to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new category, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pro5/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);
       
        category.setId(new ObjectId().toHexString());
        ResponseEntity<Void> save = categoryClient.save(category);
        return save;
    }

    /**
     * {@code PUT  /categories/:id} : Updates an existing category.
     *
     * @param id the id of the category to save.
     * @param category the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pro5/category/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") String id, @RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to update category : {}", category);    
        ResponseEntity<Void> updateCategory = categoryClient.update(id, category);
        return updateCategory;
    }

    /**
     * {@code PATCH  /categories/:id} : Partial updates given fields of an existing category, field will ignore if it is null
     *
     * @param id the id of the category to save.
     * @param category the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 404 (Not Found)} if the category is not found,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Category> partialUpdateCategory(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Category category
    ) throws URISyntaxException {
        log.debug("REST request to partial update Category partially : {}, {}", id, category);
        if (category.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, category.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Category> result = categoryService.partialUpdate(category);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, category.getId())
        );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/pro5/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.debug("REST request to get all Categories");
                   ResponseEntity<List<Category>> all = categoryClient.findAll();
        return all;
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the category to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the category, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pro5/category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable String id) {
        log.debug("REST request to get Category : {}", id);

        ResponseEntity<Category> category = categoryClient.getById(id);
        return category;
    }

   // Delete a category by ID
   @DeleteMapping("/pro5/category/{id}")
   public ResponseEntity<Void> deletePost(@PathVariable("id") String id) {
       log.debug("REST request to delete Category : {}", id);

       ResponseEntity<Void> deleteCategory = categoryClient.delete(id);
       return deleteCategory;
   }
}
