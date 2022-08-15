package ramos.maxuel.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ramos.maxuel.socialmedia.controller.dto.PostDTO;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.mapper.PostMapper;
import ramos.maxuel.socialmedia.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostMapper postMapper;
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.createPost(postMapper.toEntity(postDTO));
        return postMapper.toDto(post);
    }

    @GetMapping
    public Page<PostDTO> findPosts(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "onlyMine", required = false, defaultValue = "false") boolean onlyMine
    ) {
        return null;
    }

    @PostMapping("/{id}/repost")
    public PostDTO repost(PostDTO postDTO) {
        return null;
    }
}
