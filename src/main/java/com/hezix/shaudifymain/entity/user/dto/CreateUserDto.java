package com.hezix.shaudifymain.entity.user.dto;

import com.hezix.shaudifymain.entity.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {
    @Size(min = 4, max = 32, message = "error")
    private String username;

    private String password;

    private String passwordConfirm;
    @Email
    private String email;

    private String firstName;

    private String lastName;

    private Role role;

}
