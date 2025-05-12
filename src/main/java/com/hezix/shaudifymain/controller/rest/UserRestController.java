package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users Rest Controller")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/")
    @Operation(
            summary = "создаем пользователя",
            description = "в параметры передаем только тело пользователя"
    )
    public ResponseEntity<ReadUserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        else {
            return ResponseEntity.ok().body(userService.save(createUserDto));
        }
    }
    @GetMapping("/")
    @Operation(
            summary = "получаем всех пользователей"
    )
    public ResponseEntity<List<ReadUserDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "получаем пользователя по айди"
    )
    public ResponseEntity<ReadUserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "удаляем пользователя по айди"
    )
    public ResponseEntity<ReadUserDto> deleteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.deleteUserById(id));
    }
}
