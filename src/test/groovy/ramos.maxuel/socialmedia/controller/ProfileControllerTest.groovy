package ramos.maxuel.socialmedia.controller

import org.apache.commons.lang3.NotImplementedException
import ramos.maxuel.socialmedia.BaseIntegrationTest
import ramos.maxuel.socialmedia.controller.dto.ProfileDTO

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ProfileControllerTest extends BaseIntegrationTest {

    def "GET to /api/profile returns the profile of the authenticated user"() {
        given:

        when:
        def mvcResult = mvc.perform(get('/api/profile'))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()

        String content = mvcResult.getResponse().getContentAsString();
        ProfileDTO result = objectMapper.readValue(content, ProfileDTO.class)

        then:
        thrown(NotImplementedException)
    }

}