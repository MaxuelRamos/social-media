package ramos.maxuel.socialmedia.utils

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ramos.maxuel.socialmedia.domain.Post
import ramos.maxuel.socialmedia.domain.User
import ramos.maxuel.socialmedia.repository.PostRepository
import ramos.maxuel.socialmedia.repository.UserRepository

import java.time.ZonedDateTime

@Component
@RequiredArgsConstructor
class EntitiesUtil {

    @Autowired
    private final UserRepository userRepository

    @Autowired
    private final PostRepository postRepository

    User createUser() {
        User user = new User()
        user.setUsername(UUID.randomUUID().toString())

        return userRepository.save(user)
    }

    def Post createPost(Long userId) {
        Post post = new Post()

        post.authorId = userId
        post.message = UUID.randomUUID().toString()
        post.timestamp = ZonedDateTime.now()

        return postRepository.save(post)
    }
}
