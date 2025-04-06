package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.dto.user.CreateUserDto;
import com.hezix.shaudifymain.entity.dto.user.ReadUserDto;
import com.hezix.shaudifymain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<ReadUserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        else {
            return ResponseEntity.ok().body(userService.save(createUserDto));
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<ReadUserDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReadUserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ReadUserDto> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.deleteUserById(id));
    }
}
