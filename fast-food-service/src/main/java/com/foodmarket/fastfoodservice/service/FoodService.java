package com.foodmarket.fastfoodservice.service;

import com.foodmarket.fastfoodservice.domain.Category;
import com.foodmarket.fastfoodservice.domain.Food;
import com.foodmarket.fastfoodservice.repository.FoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService implements Serializable {
    private final CategoryService categoryService;
    private final FoodRepository foodRepository;
    private final FileStorageImageService fileStorageImageService;

    public FoodService(CategoryService categoryService, FoodRepository foodRepository, FileStorageImageService fileStorageImageService) {
        this.categoryService = categoryService;
        this.foodRepository = foodRepository;
        this.fileStorageImageService = fileStorageImageService;
    }

    public Food save(String name,
                     Double price,
                     Double rating,
                     Long categoryId,
                     MultipartFile multipartFile) {

        String imageFileHashId = fileStorageImageService.saveImageFile(multipartFile);
        Food food = new Food();
        food.setName(name);
        food.setPrice(price);
        food.setRating(rating);
        Category category = categoryService.findById(categoryId);
        food.setCategory(category);
        food.setImageUrl(imageFileHashId);

        return foodRepository.save(food);
    }

    public Food update(Food food) {
        Optional<Food> foodOptional = foodRepository.findById(food.getId());
        Food existedFood;
        if (foodOptional.isPresent())
            existedFood = foodOptional.get();
        else
            return null;
        existedFood.setName(food.getName());
        existedFood.setRating(food.getRating());
        existedFood.setPrice(food.getPrice());
        existedFood.setCategory(food.getCategory());

        return foodRepository.save(existedFood);
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public Food findById(Long id) {
        return foodRepository.findById(id).get();
    }

    public List<Food> findByName(String name) {
        return foodRepository.findByNameQuery(name);
    }

    public List<Food> findAllByName(String name) {
        return foodRepository.findAllByLike(name);
    }

    public void delete(Long id) {
        Food food = foodRepository.getById(id);
        foodRepository.delete(food);
    }

    public List<Food> findAllByCategoryId(Long categoryId) {
        return foodRepository.findAllByCategory_Id(categoryId);
    }

    public Food update(MultipartFile multipartFile, Long id, String oldImage) {
        Optional<Food> existingFoodOptional = foodRepository.findById(id);
        Food existingFood;
        if (existingFoodOptional.isPresent()) {
            existingFood = existingFoodOptional.get();
        } else
            return null;

        fileStorageImageService.delete(oldImage);
        String hashId = fileStorageImageService.saveImageFile(multipartFile);
        existingFood.setImageUrl(hashId);
        return foodRepository.save(existingFood);
    }
}
