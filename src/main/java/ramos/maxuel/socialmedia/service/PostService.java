package ramos.maxuel.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.domain.User;
import ramos.maxuel.socialmedia.repository.PostRepository;
import ramos.maxuel.socialmedia.validator.PostCreationValidator;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostCreationValidator postCreationValidator;
    private final PostRepository postRepository;
    private final UserService userService;

    public Post createPost(Post post) {
        postCreationValidator.validate(post);

        return postRepository.save(createFrom(post));
    }

    private Post createFrom(Post other) {
        Long authenticatedUserId = userService.getAuthenticatedUserId();

        Post post = new Post();
        post.setAuthorId(authenticatedUserId);
        post.setReferencePostId(other.getReferencePostId());
        post.setMessage(other.getMessage());
        post.setTimestamp(ZonedDateTime.now());

        return post;
    }
}
