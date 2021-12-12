package com.foodmarket.fastfoodservice.web.rest;


import com.foodmarket.fastfoodservice.domain.FileStorageImage;
import com.foodmarket.fastfoodservice.service.FileStorageImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class FileStorageImageResource {

    private final FileStorageImageService fileStorageImageService;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageImageResource(FileStorageImageService fileStorageImageService) {
        this.fileStorageImageService = fileStorageImageService;
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable String hashId) throws IOException {
        FileStorageImage fileStorageImage = fileStorageImageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorageImage.getName()))
                .contentType(MediaType.parseMediaType(fileStorageImage.getContentType()))
                .contentLength(fileStorageImage.getImageSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorageImage.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable String hashId) throws IOException {
        FileStorageImage fileStorageImage = fileStorageImageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, " attachment; fileName=\"" + URLEncoder.encode(fileStorageImage.getName()))
                .contentType(MediaType.parseMediaType(fileStorageImage.getContentType()))
                .contentLength(fileStorageImage.getImageSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorageImage.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId) throws FileNotFoundException {
        fileStorageImageService.delete(hashId);
        return ResponseEntity.ok("kiritilgan fayl o'chirildi");
    }
}
