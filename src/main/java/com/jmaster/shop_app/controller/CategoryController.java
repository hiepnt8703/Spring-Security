package com.jmaster.shop_app.controller;

import com.jmaster.shop_app.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok("All categories");
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok("This is a category: " + category + ".");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Update a category: " + id + ".");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Delete a category: " + id + ".");
    }

}
