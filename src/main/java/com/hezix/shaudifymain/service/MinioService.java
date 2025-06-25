package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.props.FileType;
import com.hezix.shaudifymain.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @SneakyThrows
    public void saveImage(InputStream inputStream, String fileName, long fileSize) {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getImageBucket())
                .object(fileName)
                .stream(inputStream, fileSize, -1)
                .build());
    }
    @SneakyThrows
    public void saveSong(MultipartFile file, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getSongBucket())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    public String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }
    public String getExtension(MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

    }
    @SneakyThrows
    public void createImageBucket() {
        if (!isBucketExists(FileType.IMAGE)) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getImageBucket())
                    .build());
        }
    }
    @SneakyThrows
    public void createSongBucket() {
        if (!isBucketExists(FileType.SONG)) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getSongBucket())
                    .build());
        }
    }
    @SneakyThrows
    private boolean isBucketExists(FileType fileType) {
        if(fileType == FileType.IMAGE) {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getImageBucket())
                    .build());
        }
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getSongBucket())
                .build());
    }
}
