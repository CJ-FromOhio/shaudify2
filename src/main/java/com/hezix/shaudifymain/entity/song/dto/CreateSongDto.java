package com.hezix.shaudifymain.entity.song.dto;

import com.hezix.shaudifymain.entity.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongDto {

    private Long id;
    @NotBlank(message = "поле title не должно быть пустым")
    @Size(min = 2, max = 16, message = "поле title должно быть от 2 до 16 символов")
    private String title;
    @Max(value = 64, message = "поле description должно быть до 64 символов")
    private String description;
}
