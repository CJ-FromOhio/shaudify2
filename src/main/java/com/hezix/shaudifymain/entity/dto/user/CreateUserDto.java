package com.hezix.shaudifymain.entity.dto.user;

import com.hezix.shaudifymain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {

    private String username;

    private String password;

    private String passwordConfirm;

    private String email;

    private String firstName;

    private String lastName;

    private Role role;

}
