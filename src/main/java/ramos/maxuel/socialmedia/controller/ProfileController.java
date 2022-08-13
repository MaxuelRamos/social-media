package ramos.maxuel.socialmedia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ramos.maxuel.socialmedia.controller.dto.ProfileDTO;

@RestController
@RequestMapping("/api/me")
public class ProfileController {

    @GetMapping
    ProfileDTO getProfile() {
        return null;
    }
}
