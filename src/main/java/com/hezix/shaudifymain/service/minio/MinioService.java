package com.hezix.shaudifymain.service.minio;

import com.hezix.shaudifymain.entity.web.FileType;
import com.hezix.shaudifymain.util.props.MinioProperties;
import io.micrometer.core.annotation.Timed;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class MinioService {
    private final MinioProperties minioProperties;
    private final MinioClient minioClient;



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
