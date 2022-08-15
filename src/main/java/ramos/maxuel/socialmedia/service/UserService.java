package ramos.maxuel.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ramos.maxuel.socialmedia.domain.User;
import ramos.maxuel.socialmedia.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    // Helper variable to control the authenticated user. In a real application this would be handled by an auth service
    private static Long authenticatedUserId = 1L;

    private final UserRepository userRepository;

    public User getAuthenticatedUserProfile() {
        return userRepository.findById(authenticatedUserId)
                .orElseThrow(IllegalStateException::new); // In theory, at this point, the user should be authenticated.
                                                         // Or else, the auth server would not allow the request to reach this method
    }

    /**
     * Convenience method to allow the authenticated user to be changed in the fly.
     * @param newUserId the new authenticated user
     */
    public void changeAuthenticatedUser(Long newUserId) {

        if (!userRepository.existsById(newUserId))
            throw new RuntimeException("This user does not exist");

        authenticatedUserId = newUserId;
    }

    public Long getAuthenticatedUserId() {
        return authenticatedUserId;
    }
}
