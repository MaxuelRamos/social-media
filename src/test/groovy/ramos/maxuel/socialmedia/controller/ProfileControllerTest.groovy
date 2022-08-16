package ramos.maxuel.socialmedia.controller

import org.springframework.beans.factory.annotation.Autowired
import ramos.maxuel.socialmedia.BaseIntegrationTest
import ramos.maxuel.socialmedia.controller.dto.UserDTO
import ramos.maxuel.socialmedia.controller.dto.UserProfileDTO
import ramos.maxuel.socialmedia.domain.User
import ramos.maxuel.socialmedia.repository.UserRepository
import ramos.maxuel.socialmedia.service.UserService
import ramos.maxuel.socialmedia.utils.EntitiesUtil

import java.time.LocalDateTime
import java.time.ZonedDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProfileControllerTest extends BaseIntegrationTest {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserService userService

    @Autowired
    EntitiesUtil entitiesUtil

    def "GET to /api/profile returns the profile of the authenticated user"() {
        def now = ZonedDateTime.now()
        given:

        User user = userRepository.save(new User(null, "new User", now))
        userService.changeAuthenticatedUser(user.id)
        entitiesUtil.createPost(user.id)
        entitiesUtil.createPost(user.id)

        when:
        def mvcResult = mvc.perform(get('/api/me'))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        UserProfileDTO result = objectMapper.readValue(content, UserProfileDTO.class)

        then:
        result
        user.username == result.username
        now.toInstant() == result.dateJoined.toInstant()
        2 == result.postCount
    }

}