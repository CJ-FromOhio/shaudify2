package com.hezix.shaudifymain.controller.rest;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.filter.UserFilter;
import com.hezix.shaudifymain.util.mapper.user.UserReadMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users Rest Controller")
@CustomRestControllerAdviceAnnotation
public class UserRestController {
    private final UserService userService;
    private final AuthPrincipalChecker authPrincipalChecker;
    private final UserReadMapper userReadMapper;

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

    @GetMapping("/me")
    public ResponseEntity<ReadUserDto> me(@AuthenticationPrincipal Object principal) {
        User user = authPrincipalChecker.check(principal);
        ReadUserDto dto = userReadMapper.toDto(user);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping()
    public ResponseEntity<PageResponse<ReadUserDto>> getPageAllUsers(UserFilter filter,
                                                             Pageable pageable) {
        Page<ReadUserDto> page = userService.findAllUsersByFilter(filter, pageable);
        return ResponseEntity.ok().body(PageResponse.of(page));
    }

    @GetMapping("/artists")
    public ResponseEntity<PageResponse<ReadUserDto>> getAuthors(UserFilter filter,
                                                                Pageable pageable) {
        Page<ReadUserDto> page = userService.findAllAuthors(filter, pageable);
        return ResponseEntity.ok().body(PageResponse.of(page));
    }
    @PostMapping("/makeUserAuthor")
    public ResponseEntity<ReadUserDto> makeUserAuthor(@AuthenticationPrincipal Object principal) {
        User user = authPrincipalChecker.check(principal);
        userService.makeUserAuthor(user.getId());
        ReadUserDto dto = userReadMapper.toDto(user);
        return ResponseEntity.ok().body(dto);
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
