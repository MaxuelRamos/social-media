package ramos.maxuel.socialmedia.controller

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.commons.lang3.NotImplementedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import ramos.maxuel.socialmedia.BaseIntegrationTest
import ramos.maxuel.socialmedia.controller.dto.PostDTO
import ramos.maxuel.socialmedia.domain.User
import ramos.maxuel.socialmedia.repository.PostRepository
import ramos.maxuel.socialmedia.service.UserService
import ramos.maxuel.socialmedia.utils.EntitiesUtil

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PostControllerTest extends BaseIntegrationTest {

    @Autowired
    EntitiesUtil entitiesUtil

    @Autowired
    UserService userService

    @Autowired
    PostRepository postRepository

    def "POST to /api/posts returns creates post and returns proper result when payload is valid"() {
        given:
        PostDTO postDTO = createValidPostDto()
        def user = entitiesUtil.createUser()
        userService.changeAuthenticatedUser(user.getId())

        when:
        def mvcResult = mvc.perform(
                post('/api/posts')
                        .content(objectMapper.writeValueAsString(postDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        PostDTO result = objectMapper.readValue(content, PostDTO.class)

        def persistedPost = postRepository.findById(result.id).orElseThrow()
        then:
        result
        postDTO.message == result.message
        user.id == result.authorId
        postDTO.message == persistedPost.message
        user.id == persistedPost.authorId
    }

    def "GET to /api/posts returns proper result when no parameters are informed"() {
        given:

        when:
        def mvcResult = mvc.perform(get('/api/posts'))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        Page<PostDTO> result = objectMapper.readValue(content, new TypeReference<Page<PostDTO>>() {})

        then:
        thrown(NotImplementedException)
    }

    def "GET to /api/posts returns only posts from the authenticated user when the parameter onlyMine is true"() {
        given:

        when:
        def mvcResult = mvc.perform(get('/api/posts'))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        Page<PostDTO> result = objectMapper.readValue(content, new TypeReference<Page<PostDTO>>() {})

        then:
        thrown(NotImplementedException)
    }

    def "POST to /api/post/{id}/repost reposts post and returns proper result when post id is valid"() {
        given:
        def urlTemplate = String.format('/api/posts/%d/repost', 1)
        when:
        def mvcResult = mvc.perform(post(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        PostDTO result = objectMapper.readValue(content, PostDTO.class)

        then:
        thrown(NotImplementedException)
    }

    PostDTO createValidPostDto() {
        PostDTO postDTO = new PostDTO()

        postDTO.setMessage("message")

        return postDTO
    }
}