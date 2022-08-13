package ramos.maxuel.socialmedia.controller

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.commons.lang3.NotImplementedException
import org.springframework.data.domain.Page
import ramos.maxuel.socialmedia.BaseIntegrationTest
import ramos.maxuel.socialmedia.controller.dto.PostDTO

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PostControllerTest extends BaseIntegrationTest {

    def "GET to /api/posts returns proper result when no parameters are informed"() {
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

    def "POST to /api/posts returns creates post and returns proper result when payload is valid"() {
        given:

        when:
        def mvcResult = mvc.perform(post('/api/posts'))
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

}