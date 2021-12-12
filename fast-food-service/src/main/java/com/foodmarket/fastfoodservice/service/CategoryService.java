package com.foodmarket.fastfoodservice.service;

import com.foodmarket.fastfoodservice.domain.Category;
import com.foodmarket.fastfoodservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    public final CategoryRepository categoryRepository;
    private final FileStorageImageService fileStorageImageService;
    @Value("${upload.folder}")
    private String uploadFolder;

    public CategoryService(CategoryRepository categoryRepository, FileStorageImageService fileStorageImageService) {
        this.categoryRepository = categoryRepository;
        this.fileStorageImageService = fileStorageImageService;
    }

    public Category save(String name, MultipartFile multipartFile) {
        Category category = new Category();
        category.setName(name);

        String hashId = fileStorageImageService.saveImageFile(multipartFile);
        category.setImageHashId(hashId);
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByNameQuery(name);
    }

    public List<Category> findAllByParam(String name) {
        return categoryRepository.findAllByLike(name);
    }

    public void delete(Long id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.delete(category);
    }

    public Category update(Long id, String name, String hash, MultipartFile multipartFile) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
        Category existingCategory;
        if (existingCategoryOptional.isPresent()) {
            existingCategory = existingCategoryOptional.get();
        } else
            return null;

        existingCategory.setName(name);
        fileStorageImageService.delete(hash);
        String hashId = fileStorageImageService.saveImageFile(multipartFile);
        existingCategory.setImageHashId(hashId);
        return categoryRepository.save(existingCategory);
    }
}
