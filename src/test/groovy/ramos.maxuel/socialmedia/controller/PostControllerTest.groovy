package ramos.maxuel.socialmedia.controller

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.commons.lang3.NotImplementedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import ramos.maxuel.socialmedia.BaseIntegrationTest
import ramos.maxuel.socialmedia.controller.dto.PostDTO
import ramos.maxuel.socialmedia.domain.Post
import ramos.maxuel.socialmedia.domain.User
import ramos.maxuel.socialmedia.repository.PostRepository
import ramos.maxuel.socialmedia.service.UserService
import ramos.maxuel.socialmedia.utils.EntitiesUtil
import ramos.maxuel.socialmedia.utils.RestPageImpl

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

    def "POST to posts returns creates post and returns proper result when payload is valid"() {
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

    def "GET to posts returns proper result when no parameters are informed"() {
        given:
        def user1 = entitiesUtil.createUser()
        def p1 = entitiesUtil.createPost(user1.id)

        def user2 = entitiesUtil.createUser()
        def p2 = entitiesUtil.createPost(user2.id)

        def user3 = entitiesUtil.createUser()
        def p3 = entitiesUtil.createPost(user3.id)

        when:
        def mvcResult = mvc.perform(get('/api/posts'))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        def content = mvcResult.getResponse().getContentAsString()
        def resultPage = objectMapper.readValue(content, new TypeReference<RestPageImpl<PostDTO>>() {})

        then:
        resultPage
        resultPage.content
        resultPage.content.size() >= 3 // Other posts might exist. We only care about the last three
        resultPage.content[2].getId() == p1.getId()
        resultPage.content[2].getMessage() == p1.getMessage()
        resultPage.content[1].getId() == p2.getId()
        resultPage.content[1].getMessage() == p2.getMessage()
        resultPage.content[0].getId() == p3.getId()
        resultPage.content[0].getMessage() == p3.getMessage()
    }

    def "GET to posts returns only posts from the authenticated user when the parameter onlyMine is true"() {
        given:
        def user1 = entitiesUtil.createUser()
        def p1 = entitiesUtil.createPost(user1.id)
        userService.changeAuthenticatedUser(user1.id)

        // Control group
        def user2 = entitiesUtil.createUser()
        entitiesUtil.createPost(user2.id)

        def user3 = entitiesUtil.createUser()
        entitiesUtil.createPost(user3.id)

        when:
        def mvcResult = mvc.perform(get('/api/posts?onlyMine=true'))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        def content = mvcResult.getResponse().getContentAsString()
        def resultPage = objectMapper.readValue(content, new TypeReference<RestPageImpl<PostDTO>>() {})

        then:
        resultPage
        resultPage.content
        resultPage.content.size() == 1 // Other posts might exist. We only care about the last three
        resultPage.content[0].getId() == p1.getId()
        resultPage.content[0].getMessage() == p1.getMessage()
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