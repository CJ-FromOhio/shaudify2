package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.entity.user.form.CreateUserFormDto;
import com.hezix.shaudifymain.entity.web.PageResponse;
import com.hezix.shaudifymain.util.filter.UserFilter;
import com.hezix.shaudifymain.service.song.LikedSongService;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.BindingResultParser;
import com.hezix.shaudifymain.util.mapper.user.UserReadMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@CustomControllerAdviceAnnotation
public class UserController {
    private final UserService userService;
    private final UserReadMapper userReadMapper;
    private final BindingResultParser bindingResultParser;

    private final AuthPrincipalChecker authPrincipalChecker;

    @GetMapping()
    public String get(Model model,
                      UserFilter filter,
                      Pageable pageable) {
        Page<ReadUserDto> page = userService.findAllUsersByFilter(filter, pageable);

        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "users/all_users";
    }

    @GetMapping("/artists")
    public String getAuthors(Model model,
                             UserFilter filter,
                             Pageable pageable) {
        Page<ReadUserDto> page = userService.findAllAuthors(filter, pageable);

        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "users/all_authors";
    }
    @GetMapping("/me")
    public String me(@AuthenticationPrincipal Object principal, Model model) {
        User user = authPrincipalChecker.check(principal);
        ReadUserDto readUserDto = userReadMapper.toDto(user);
        model.addAttribute("user", readUserDto);
        return "users/me";
    }
    @GetMapping("/createUser")
    public String createUser(Model model) {
        model.addAttribute("createUserFormDto", new CreateUserFormDto());
        return "users/create_user";
    }
    @PostMapping("/makeUserAuthor")
    public String makeUserAuthor(@AuthenticationPrincipal Object principal) {
        User user = authPrincipalChecker.check(principal);
        userService.makeUserAuthor(user.getId());
        return "redirect:/users/me";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute CreateUserFormDto createUserFormDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "users/create_user";
        }
        var createUserDto = createUserFormDto.getCreateUserDto();
        var imageFile = createUserFormDto.getImageFile();
        Long id = userService.save(createUserDto).getId();
        userService.uploadImage(id, imageFile);
        return "redirect:/users/" + id;
    }

    @GetMapping("/complete_profile")
    public String completeProfile(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        var createUserFormDto = new CreateUserFormDto();
        var userCreateDto = createUserFormDto.getCreateUserDto();

        userCreateDto.setEmail(oidcUser.getEmail());

        if (oidcUser.getGivenName() != null) {
            userCreateDto.setFirstName(oidcUser.getGivenName());
        }
        if (oidcUser.getFamilyName() != null) {
            userCreateDto.setLastName(oidcUser.getFamilyName());
        }

        model.addAttribute("createUserFormDto", createUserFormDto);
        return "users/complete_profile";
    }

    @PostMapping("/complete_profile")
    public String completeProfile(@Valid @ModelAttribute CreateUserFormDto createUserFormDto,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "users/complete_profile";
        }
        var createUserDto = createUserFormDto.getCreateUserDto();
        var imageFile = createUserFormDto.getImageFile();

        var updated = userService.save(createUserDto);
        userService.uploadImage(updated.getId(), imageFile);
        return "redirect:/users/" + updated.getId();
    }

    @GetMapping("/{id}")
    public String user(@PathVariable Long id, Model model,
                       @AuthenticationPrincipal Object principal) {
        ReadUserDto user = userReadMapper.toDto(authPrincipalChecker.check(principal));
        if(user.getId().equals(id)) {
            model.addAttribute("user", user);
            return "redirect:/users/me";
        }
        ReadUserDto userById = userService.findUserById(id);
        model.addAttribute("user", userById);
        return "users/user_by_id";
    }
}
