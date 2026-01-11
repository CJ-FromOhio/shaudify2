package com.hezix.shaudifymain.service.minio;


import com.hezix.shaudifymain.util.exception.FileUploadException;
import io.micrometer.core.annotation.Timed;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioImageService {

    private final MinioService minioService;
    @Timed(value = "service.minio.upload",
            description = "upload file to minio")
    public String upload (MultipartFile image){
        try {
            minioService.createImageBucket();
        }catch(Exception e){
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        String originalFilename = image.getOriginalFilename();
        if(image.isEmpty() || originalFilename == null){
            throw new FileUploadException("image upload failed. image must have name");
        }
        String fileName = minioService.generateFileName(image);
        InputStream inputStream;

        try{
            inputStream = image.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        saveImage(inputStream, fileName, image.getSize());
        return fileName;
    }
    @Timed(value = "service.minio.save",
            description = "file saving",
            extraTags ={"method","image"},
            histogram = true)
    @SneakyThrows
    public void saveImage(InputStream inputStream, String fileName, long fileSize) {
        minioService.getMinioClient().putObject(PutObjectArgs.builder()
                .bucket(minioService.getMinioProperties().getImageBucket())
                .object(fileName)
                .stream(inputStream, fileSize, -1)
                .build());
    }
}
