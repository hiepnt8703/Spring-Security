package com.jmaster.shop_app.controller;

import com.jmaster.shop_app.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {

    @GetMapping
    public ResponseEntity<String> getAllCategories(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok("All categories");
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> error = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(error);
        }

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
