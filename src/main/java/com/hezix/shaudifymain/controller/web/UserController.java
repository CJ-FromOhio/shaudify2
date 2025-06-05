package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.SongService;
import com.hezix.shaudifymain.service.UserService;
import com.hezix.shaudifymain.util.BindingResultParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@CustomControllerAdviceAnnotation
public class UserController {
    private final UserService userService;
    private final BindingResultParser bindingResultParser;
    private final SongService songService;

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
    public String createUser(@Valid @ModelAttribute CreateUserDto createUserDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResultParser.parseToString(bindingResult));
            return "users/create_user";
        }
        Long id = userService.save(createUserDto).getId();
        return "redirect:/users/" + id;
    }
    @GetMapping("/{id}")
    public String user(@PathVariable Long id, Model model) {
        ReadUserDto userById = userService.findUserById(id);
        List<ReadSongDto> likedSongList = songService.findSonsIdgByLikedSongList(userById.getLikedSongs());
        model.addAttribute("user", userById);
        model.addAttribute("songs", likedSongList);
        return "users/user_by_id";
    }
}
