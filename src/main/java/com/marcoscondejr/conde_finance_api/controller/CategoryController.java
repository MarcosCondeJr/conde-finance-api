package com.marcoscondejr.conde_finance_api.controller;

import com.marcoscondejr.conde_finance_api.dto.category.CategoryRequestDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryUpadateDTO;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        List<CategoryResponseDTO> categories = this.service.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") String id) {
        CategoryResponseDTO category = this.service.getCategoryById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> saveCategory(@RequestBody @Valid CategoryRequestDTO data) {
        CategoryResponseDTO category = this.service.saveCategory(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable("id") String id, @RequestBody @Valid CategoryUpadateDTO data
    ) {
        CategoryResponseDTO category = this.service.updateCategory(Long.parseLong(id), data);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") String id) {
        this.service.deleteCategory(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
