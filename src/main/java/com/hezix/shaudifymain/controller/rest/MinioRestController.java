package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.props.MinioProperties;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.internal.http2.Header;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/minio")
@RequiredArgsConstructor
public class MinioRestController {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @SneakyThrows
    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try(InputStream is = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioProperties.getImageBucket())
                        .object(fileName)
                        .build()
        )){
            byte[] bytes = is.readAllBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return ResponseEntity.ok(bytes);
        }
    }
    @SneakyThrows
    @GetMapping("/songs/{fileName}")
    public ResponseEntity<byte[]> getSong(@PathVariable String fileName) {
        try(InputStream is = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioProperties.getSongBucket())
                        .object(fileName)
                        .build()
        )){
            byte[] bytes = is.readAllBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/mpeg"));
            return ResponseEntity.ok(bytes);
        }
    }
}
