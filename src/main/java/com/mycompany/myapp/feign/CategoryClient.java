package com.mycompany.myapp.feign;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycompany.myapp.domain.Category;



@FeignClient(name = "restheart-category", url = "http://localhost:8080", decode404 = true)
public interface CategoryClient {

    @PostMapping("/pro5/category")
    public ResponseEntity<Void> save(@RequestBody com.mycompany.myapp.domain.Category category) throws URISyntaxException;


     // Method to get a category by id (GET request)
    @GetMapping("/pro5/category/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") String id);

     // Method to get all pcategory (GET request)
     @GetMapping("/pro5/category")  // Assuming the endpoint for all posts is "/blogApps/posts"
     public ResponseEntity<List<Category>> findAll();

      // Method to update an existing category (PUT request)
    @PutMapping("/pro5/category/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody Category category) throws URISyntaxException;

     // Method to delete a category by id (DELETE request)
    @DeleteMapping("/pro5/category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id);


    
}
