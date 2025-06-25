package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.song.SongFiles;
import com.hezix.shaudifymain.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioImageService {

    private final MinioService minioService;

    public String upload (SongFiles image){
        try {
            minioService.createImageBucket();
        }catch(Exception e){
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        MultipartFile file = image.getImageFile();
        if(file.isEmpty() || file.getOriginalFilename() == null){
            throw new FileUploadException("image upload failed. image must have name");
        }
        String fileName = minioService.generateFileName(file);
        InputStream inputStream;

        try{
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        minioService.saveObject(inputStream, fileName);
        return fileName;
    }

}
