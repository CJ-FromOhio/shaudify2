package com.hezix.shaudifymain.util.filter;

import com.hezix.shaudifymain.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserFilter {
    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private Role role;
}
