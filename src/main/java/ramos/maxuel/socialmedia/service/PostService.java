package ramos.maxuel.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.exception.ResourceNotFoundException;
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

    public Page<Post> findByParams(boolean onlyMine, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "timestamp");

        if (onlyMine) {
            Long userId = userService.getAuthenticatedUserId();
            return postRepository.findByAuthorId(userId, pageRequest);
        }

        return postRepository.findAll(pageRequest);
    }

    public Post repost(Long postId, Post post) {
        boolean postExists = postRepository.existsById(postId);

        if(!postExists) {
            throw new ResourceNotFoundException();
        }

        post.setReferencePostId(postId);
        Post finalPostObj = createFrom(post);

        postCreationValidator.validate(finalPostObj);

        return postRepository.save(finalPostObj);
    }
}
