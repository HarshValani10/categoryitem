package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Item entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends MongoRepository<Item, String> {}
