package ramos.maxuel.socialmedia.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ramos.maxuel.socialmedia.controller.dto.PostDTO;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @GetMapping
    Page<PostDTO> findPosts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "onlyMine", required = false, defaultValue = "false") boolean onlyMine
    ) {
        return null;
    }

    @PostMapping
    PostDTO createPost(PostDTO postDTO) {
        return null;
    }

    @PostMapping("/{id}/repost")
    PostDTO repost(PostDTO postDTO) {
        return null;
    }
}
