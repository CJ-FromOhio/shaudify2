package com.hezix.shaudifymain.entity.user.form;


import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserFormDto {
    private CreateUserDto createUserDto;
    private MultipartFile imageFile;
}
