package com.hezix.shaudifymain.service.minio;


import com.hezix.shaudifymain.util.exception.FileUploadException;
import io.micrometer.core.annotation.Timed;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MinioSongService {

    private final MinioService minioService;

    @Transactional
    public String upload (MultipartFile file){
        try {
            minioService.createSongBucket();
        }catch(Exception e){
            throw new FileUploadException("Song upload failed" + e.getMessage());
        }
        if(file.isEmpty() || file.getOriginalFilename() == null){
            throw new FileUploadException("song upload failed. image must have name");
        }
        String fileName = minioService.generateFileName(file);

        saveSong(file, fileName);
        return fileName;
    }

    @Timed(value = "service.minio.save",
            description = "file saving",
            extraTags ={"method","song"})
    @SneakyThrows
    private void saveSong(MultipartFile file, String fileName) {

        minioService
                .getMinioClient().putObject(PutObjectArgs.builder()
                .bucket(minioService.getMinioProperties().getSongBucket())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }
}
