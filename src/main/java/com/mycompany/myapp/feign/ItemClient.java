package com.mycompany.myapp.feign;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale.Category;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.myapp.domain.Item;
import com.mycompany.myapp.domain.RefType;
import com.mycompany.myapp.domain.RefType.RefTo;

@FeignClient(name = "restheart-item", url = "http://localhost:8080", decode404 = true)
public interface ItemClient {
  // Method to save a new comment (POST request)
  @PostMapping("/pro5/item")
  public ResponseEntity<Void> save(@RequestBody Item item) throws URISyntaxException;

  // Method to get a comment by id (GET request)
    @GetMapping("/pro5/item/{id}")
    public ResponseEntity<Item> getById(@PathVariable("id") String id);

    // Method to get all comments (GET request)
    @GetMapping("/pro5/item")
    public ResponseEntity<List<Item>> findAll();

    // Method to update an existing comment (PUT request)
    @PutMapping("/pro5/item/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody Item item) throws URISyntaxException;

    // Method to delete a item by id (DELETE request)
    @DeleteMapping("/pro5/item/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);

    
  }
