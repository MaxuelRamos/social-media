package ramos.maxuel.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ramos.maxuel.socialmedia.assembler.PostVOToPostAssembler;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.domain.PostVO;
import ramos.maxuel.socialmedia.exception.ResourceNotFoundException;
import ramos.maxuel.socialmedia.repository.PostRepository;
import ramos.maxuel.socialmedia.validator.PostCreationValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostCreationValidator postCreationValidator;
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostVOToPostAssembler postAssembler;

    public Post createPost(Post post) {
        postCreationValidator.validate(post);

        return postRepository.save(createFrom(post));
    }

    private Post createFrom(Post other) {
        Post post = new Post();

        if (other != null) {
            post.setMessage(other.getMessage());
        }

        Long authenticatedUserId = userService.getAuthenticatedUserId();

        post.setAuthorId(authenticatedUserId);
        post.setTimestamp(ZonedDateTime.now());

        return post;
    }

    public Page<Post> findByParams(boolean onlyMine, ZonedDateTime start, ZonedDateTime end, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "timestamp");

        Long userId = onlyMine ? userService.getAuthenticatedUserId() : null;

        Page<PostVO> result = postRepository.findByParams(userId, start, end, pageRequest);
        return result.map(postAssembler::assemble);
    }

    public Post repost(Long postId, Post post) {
        Post finalPostObj = createFrom(post);

        finalPostObj.setReferencePostId(postId);

        postCreationValidator.validate(finalPostObj);

        return postRepository.save(finalPostObj);
    }
}
