package ramos.maxuel.socialmedia.controller

import org.springframework.beans.factory.annotation.Autowired
import ramos.maxuel.socialmedia.BaseIntegrationTest
import ramos.maxuel.socialmedia.controller.dto.UserDTO
import ramos.maxuel.socialmedia.domain.User
import ramos.maxuel.socialmedia.repository.UserRepository
import ramos.maxuel.socialmedia.service.UserService

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProfileControllerTest extends BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    def "GET to /api/profile returns the profile of the authenticated user"() {
        given:
        User user = userRepository.save(new User(null, "new User"))
        userService.changeAuthenticatedUser(user.id)

        when:
        def mvcResult = mvc.perform(get('/api/me'))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        UserDTO result = objectMapper.readValue(content, UserDTO.class)

        then:
        result
        user.username == result.username
    }

}