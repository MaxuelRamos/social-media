package ramos.maxuel.socialmedia.validator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.domain.PostType;
import ramos.maxuel.socialmedia.exception.Error;
import ramos.maxuel.socialmedia.repository.PostRepository;
import ramos.maxuel.socialmedia.service.UserService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
public class PostCreationValidator implements Validator<Post> {

    private static final int MAX_MESSAGE_SIZE = 777;

    private final PostRepository postRepository;
    private final UserService userService;

    public List<Function<Post, Optional<Error>>> getValidations() {
        return List.of(
                this::validateMessage,
                this::validateReferencePost,
                this::validateAuthor
        );
    }

    private Optional<Error> validateMessage(Post post) {
        Error error = null;

        PostType postType = post.getType();
        boolean shouldValidateMessage = postType == PostType.ORIGINAL || !isBlank(post.getMessage());

        if (shouldValidateMessage && (isBlank(post.getMessage()) || post.getMessage().length() >= MAX_MESSAGE_SIZE)) {
            error = new Error("Invalid message");
        }

        return Optional.ofNullable(error);
    }

    private Optional<Error> validateReferencePost(Post post) {
        Error error = null;

        if (post.getReferencePostId() != null) {
            boolean referencePostExists = postRepository.existsById(post.getReferencePostId());

            if (!referencePostExists) {
                error = new Error("Reference post does not exist.");
            }
        }

        return Optional.ofNullable(error);
    }

    private Optional<Error> validateAuthor(Post post) {
        ZonedDateTime yesterday = ZonedDateTime.now().minusDays(1);
        Long userId = userService.getAuthenticatedUserId();
        int countInTheLast24H = postRepository.countByAuthorIdAndTimestampGreaterThan(userId, yesterday);
        Error error = null;

        if (countInTheLast24H >= 5) {
            error = new Error("Users can only create 5 posts per day.");
        }

        return Optional.ofNullable(error);
    }
}
