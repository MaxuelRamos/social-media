package ramos.maxuel.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ramos.maxuel.socialmedia.controller.dto.UserDTO;
import ramos.maxuel.socialmedia.domain.User;
import ramos.maxuel.socialmedia.mapper.UserMapper;
import ramos.maxuel.socialmedia.service.UserService;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public UserDTO getProfile() {
        User authenticatedUser = userService.getAuthenticatedUserProfile();
        return userMapper.toDto(authenticatedUser);
    }

    @PutMapping("/{newUserId}")
    public void changeAuthenticatedUser(@PathVariable Long newUserId) {
        userService.changeAuthenticatedUser(newUserId);
    }
}
