package ramos.maxuel.socialmedia.validator

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import ramos.maxuel.socialmedia.domain.Post
import ramos.maxuel.socialmedia.exception.BusinessException
import ramos.maxuel.socialmedia.repository.PostRepository
import ramos.maxuel.socialmedia.service.UserService
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.doReturn

@ExtendWith(MockitoExtension.class)
class PostCreationValidatorTest extends Specification {

    private static final Long USER_ID = 1L
    private static final Long REFERENCE_POST_ID = 2L

    @Mock
    PostRepository postRepository

    @Mock
    UserService userService

    @InjectMocks
    PostCreationValidator validator

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this)
    }

    def "validate does not throw exception when post is valid"() {
        given:
        def post = createValidPost()

        doReturn(0).when(postRepository).countByAuthorIdAndTimestampGreaterThan(any(), any())
        doReturn(true).when(postRepository).existsById(REFERENCE_POST_ID)
        doReturn(USER_ID).when(userService).getAuthenticatedUserId()

        when:
        validator.validate(post)

        then:
        true // Does not throw exception
    }

    def "validate throws exception when message is empty" () {
        def post = createValidPost()
        post.message = ''
        post.referencePostId = null

        doReturn(0).when(postRepository).countByAuthorIdAndTimestampGreaterThan(any(), any())
        doReturn(true).when(postRepository).existsById(REFERENCE_POST_ID)
        doReturn(USER_ID).when(userService).getAuthenticatedUserId()

        when:
        validator.validate(post)

        then:
        thrown BusinessException
    }

    def "validate throws exception when reference post does not exist" () {
        def post = createValidPost()

        doReturn(0).when(postRepository).countByAuthorIdAndTimestampGreaterThan(any(), any())
        doReturn(false).when(postRepository).existsById(REFERENCE_POST_ID)
        doReturn(USER_ID).when(userService).getAuthenticatedUserId()

        when:
        validator.validate(post)

        then:
        thrown BusinessException
    }

    def "validate throws exception when has 5 post in the last 24 hours" () {
        def post = createValidPost()

        doReturn(5).when(postRepository).countByAuthorIdAndTimestampGreaterThan(any(), any())
        doReturn(true).when(postRepository).existsById(REFERENCE_POST_ID)
        doReturn(USER_ID).when(userService).getAuthenticatedUserId()

        when:
        validator.validate(post)

        then:
        thrown BusinessException
    }

    Post createValidPost() {
        def post = new Post()

        post.message = UUID.randomUUID().toString()
        post.authorId = USER_ID
        post.referencePostId = REFERENCE_POST_ID

        return post
    }
}