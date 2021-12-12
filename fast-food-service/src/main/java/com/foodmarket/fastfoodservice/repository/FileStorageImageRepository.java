package com.foodmarket.fastfoodservice.repository;

import com.foodmarket.fastfoodservice.domain.FileStorageImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageImageRepository extends JpaRepository<FileStorageImage, Long> {
    FileStorageImage findByHashId(String hashId);
}
