package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.song.SongImage;
import com.hezix.shaudifymain.exception.FileUploadException;
import com.hezix.shaudifymain.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final MinioService minioService;

    public String upload (SongImage image){
        try {
            minioService.createBucket();
        }catch(Exception e){
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        MultipartFile file = image.getFile();
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
        minioService.saveImage(inputStream, fileName);
        return fileName;
    }

}
