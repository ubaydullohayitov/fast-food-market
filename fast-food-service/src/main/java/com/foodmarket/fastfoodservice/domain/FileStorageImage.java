package com.foodmarket.fastfoodservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class FileStorageImage implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String hashId;

    private String contentType;

    private Long ImageSize;

    private String uploadPath;

    private String extension;

    @Enumerated(EnumType.STRING)
    private FileStorageImageStatus fileStorageImageStatus;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getImageSize() {
        return ImageSize;
    }

    public void setImageSize(Long imageSize) {
        ImageSize = imageSize;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileStorageImageStatus getFileStorageImageStatus() {
        return fileStorageImageStatus;
    }

    public void setFileStorageImageStatus(FileStorageImageStatus fileStorageImageStatus) {
        this.fileStorageImageStatus = fileStorageImageStatus;
    }
}
