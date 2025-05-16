package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public String get(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users/all_users";
    }
    @GetMapping("/createUser")
    public String createUser(Model model) {
        model.addAttribute("createUserDto", new CreateUserDto());
        return "users/create_user";
    }
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute CreateUserDto createUserDto,
                             Model model) {
        userService.save(createUserDto);
        return "users/all_users";
    }
    @GetMapping("/{id}")
    public String user(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/user_by_id";
    }
}
