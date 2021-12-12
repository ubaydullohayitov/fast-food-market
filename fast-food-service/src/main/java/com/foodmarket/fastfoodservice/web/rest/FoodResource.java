package com.foodmarket.fastfoodservice.web.rest;

import com.foodmarket.fastfoodservice.domain.Food;
import com.foodmarket.fastfoodservice.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodResource {

    public final FoodService foodService;

    public FoodResource(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/foods")
    public ResponseEntity create(@RequestParam String name,
                                 @RequestParam Double price,
                                 @RequestParam Double rating,
                                 @RequestPart("image") MultipartFile multipartFile) {
        Food food1 = foodService.save(name, price, rating, multipartFile);
        return ResponseEntity.ok(food1);
    }

    @GetMapping("/foods")
    public ResponseEntity getAll() {
        List<Food> foodList = foodService.findAll();
        return ResponseEntity.ok(foodList);
    }

    @PutMapping("/foods/{id}")
    public ResponseEntity update(@RequestBody Food foodNew) {
        Food food1 = foodService.update(foodNew);
        return ResponseEntity.ok(food1);
    }

    @PutMapping("/food")
    public ResponseEntity updateImage(@RequestPart("image") MultipartFile multipartFile,
                                      @RequestParam Long id,
                                      @RequestParam String oldImage) {
        Food food1 = foodService.update(multipartFile, id, oldImage);
        return ResponseEntity.ok(food1);
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        Food food = foodService.findById(id);
        return ResponseEntity.ok(food);
    }

    @GetMapping("/foods/search")
    public ResponseEntity getAllSearch(@RequestParam String query) {
        List<Food> food = foodService.findAllByName(query);
        return ResponseEntity.ok(food);
    }

    @DeleteMapping("/foods/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        foodService.delete(id);
        return ResponseEntity.ok("Berilgan aydi bo'yicha kategoriya o'chirildi");
    }

    @GetMapping("/foods/by_category_id")
    public ResponseEntity getAllByCategoryId(@RequestParam Long categoryId) {
        List<Food> foodList = foodService.findAllByCategoryId(categoryId);
        return ResponseEntity.ok(foodList);
    }
}
