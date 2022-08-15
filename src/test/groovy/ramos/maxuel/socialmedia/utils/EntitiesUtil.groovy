package ramos.maxuel.socialmedia.utils

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ramos.maxuel.socialmedia.domain.User
import ramos.maxuel.socialmedia.repository.UserRepository

@Component
@RequiredArgsConstructor
class EntitiesUtil {

    @Autowired
    private final UserRepository userRepository

    User createUser() {
        User user = new User()
        user.setUsername(UUID.randomUUID().toString())

        return userRepository.save(user)
    }
}
