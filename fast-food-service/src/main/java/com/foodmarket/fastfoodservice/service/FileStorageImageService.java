package com.foodmarket.fastfoodservice.service;

import com.foodmarket.fastfoodservice.domain.FileStorageImage;
import com.foodmarket.fastfoodservice.domain.FileStorageImageStatus;
import com.foodmarket.fastfoodservice.repository.FileStorageImageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FileStorageImageService {

    private final FileStorageImageRepository fileStorageImageRepository;

    private final Hashids hashids;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageImageService(FileStorageImageRepository fileStorageImageRepository) {
        this.fileStorageImageRepository = fileStorageImageRepository;
        this.hashids = new Hashids(getClass().getName(), 6);
    }

    public String saveImageFile(MultipartFile multipartFile) {
        FileStorageImage fileStorageImage = new FileStorageImage();
        fileStorageImage.setName(multipartFile.getOriginalFilename());
        fileStorageImage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorageImage.setImageSize(multipartFile.getSize());
        fileStorageImage.setContentType(multipartFile.getContentType());
        fileStorageImage.setFileStorageImageStatus(FileStorageImageStatus.DRAFT);

        fileStorageImageRepository.save(fileStorageImage);

        File uploadFolder = new File(String.format("%s/upload_fileImages/", this.uploadFolder));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("Papkalar yaratildi");
        }
        fileStorageImage.setHashId(hashids.encode(fileStorageImage.getId()));
        fileStorageImage.setUploadPath(String.format("upload_fileImages/%s.%s",
                fileStorageImage.getHashId(),
                fileStorageImage.getExtension()));
        fileStorageImageRepository.save(fileStorageImage);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format(
                "%s.%s", fileStorageImage.getHashId(), fileStorageImage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStorageImage.getHashId();
    }

    @Transactional(readOnly = true)
    public FileStorageImage findByHashId(String hashId) {
        return fileStorageImageRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        FileStorageImage fileStorageImage = fileStorageImageRepository.findByHashId(hashId);
        if (fileStorageImage == null)
            return;
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorageImage.getUploadPath()));
        if (file.delete()) {
            fileStorageImageRepository.delete(fileStorageImage);
        }
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}
