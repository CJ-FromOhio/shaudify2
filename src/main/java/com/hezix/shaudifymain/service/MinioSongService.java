package com.hezix.shaudifymain.service;


import com.hezix.shaudifymain.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
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

        minioService.saveSong(file, fileName);
        return fileName;
    }
}
