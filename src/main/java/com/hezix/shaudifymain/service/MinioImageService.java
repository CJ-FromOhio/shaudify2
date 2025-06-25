package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.files.ImageFile;
import com.hezix.shaudifymain.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioImageService {

    private final MinioService minioService;

    public String upload (MultipartFile image){
        try {
            minioService.createImageBucket();
        }catch(Exception e){
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        if(image.isEmpty() || image.getOriginalFilename() == null){
            throw new FileUploadException("image upload failed. image must have name");
        }
        String fileName = minioService.generateFileName(image);
        InputStream inputStream;

        try{
            inputStream = image.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        minioService.saveImage(inputStream, fileName, image.getSize());
        return fileName;
    }

}
