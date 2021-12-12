package com.foodmarket.fastfoodservice.web.rest;


import com.foodmarket.fastfoodservice.domain.Category;
import com.foodmarket.fastfoodservice.security.SecurityUtils;
import com.foodmarket.fastfoodservice.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryResource {

    public final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity create(@RequestPart("image") MultipartFile multipartFile, @RequestParam String name) {
        Category category1 = categoryService.save(name, multipartFile);
        return ResponseEntity.ok(category1);
    }

    @PutMapping("/categories")
    public ResponseEntity update(@RequestPart("image") MultipartFile multipartFile,
                                 @RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam String hash) {
        Category category1 = categoryService.update(id, name, hash, multipartFile);
        return ResponseEntity.ok(category1);
    }

    @GetMapping("/categories")
    public ResponseEntity getAll() {
        List<Category> categoryList = categoryService.findAll();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/categories/{name}")
    public ResponseEntity getByName(@PathVariable String name) {
        Optional<String> optional = SecurityUtils.getCurrentUserName();
        List<Category> categoryList = categoryService.findByName(name);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/categories/search")
    public ResponseEntity getAllSearch(@RequestParam String name) {
        List<Category> categoryList = categoryService.findAllByParam(name);
        return ResponseEntity.ok(categoryList);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Kiritilgan taomlar o'chirildi");
    }
}
