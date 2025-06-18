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

    public String upload (SongImage image){
        try {
            createBucket();
        }catch(Exception e){
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        MultipartFile file = image.getFile();
        if(file.isEmpty() || file.getOriginalFilename() == null){
            throw new FileUploadException("image upload failed. image must have name");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;

        try{
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("Image upload failed" + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;
    }
    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
     minioClient.putObject(PutObjectArgs.builder()
             .bucket(minioProperties.getBucket())
             .object(fileName)
             .stream(inputStream, inputStream.available(), -1)
             .build());
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }
    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

    }
    @SneakyThrows
    private void createBucket() {
        boolean foundedBucket = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!foundedBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }
}
