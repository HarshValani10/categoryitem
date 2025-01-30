package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Item;
import com.mycompany.myapp.domain.RefType;
import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Item}.
 */
@Service
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save a item.
     *
     * @param item the entity to save.
     * @return the persisted entity.
     */
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        return itemRepository.save(item);
    }

    /**
     * Update a item.
     *
     * @param item the entity to save.
     * @return the persisted entity.
     */
    public Item update(Item item) {
        log.debug("Request to save Item : {}", item);
        return itemRepository.save(item);
    }

    /**
     * Partially update a item.
     *
     * @param item the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Item> partialUpdate(Item item) {
        log.debug("Request to partially update Item : {}", item);

        return itemRepository
            .findById(item.getId())
            .map(existingItem -> {
                if (item.getName() != null) {
                    existingItem.setName(item.getName());
                }
                if (item.getPrice() != null) {
                    existingItem.setPrice(item.getPrice());
                }

                return existingItem;
            })
            .map(itemRepository::save);
    }

    /**
     * Get all the items.
     *
     * @return the list of entities.
     */
    public List<Item> findAll() {
        log.debug("Request to get all Items");
        return itemRepository.findAll();
    }

    /**
     * Get one item by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Item> findOne(String id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findById(id);
    }

    /**
     * Delete the item by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
    }

    public ResponseEntity<?> savebyid(String catId, Item item) {




        Category category = categoryRepository.findById(catId).get();

        category.getItem().add(new RefType( catId , RefType.RefTo.item));

        Category save = categoryRepository.save(category);

        item.setCategory(new RefType(category.getId() , RefType.RefTo.category));
        itemRepository.save(item);

        return  ResponseEntity.ok(save);

    }
}
