package ramos.maxuel.socialmedia

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification;

@AutoConfigureMockMvc
@SpringBootTest
class BaseIntegrationTest extends Specification {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mvc


}