package ramos.maxuel.socialmedia

import ramos.maxuel.socialmedia.domain.Post
import ramos.maxuel.socialmedia.domain.PostType
import spock.lang.Specification
import spock.lang.Unroll

class PostTest extends Specification {

    @Unroll
    def "getPostType returns [#expectedResult] when message is [#message] and referencePostExists is [#referencePostExists] "(String message, boolean referencePostExists, PostType expectedResult) {
        given:
        def post = new Post()
        post.message = message

        if(referencePostExists)
            post.referencePostId = 1L

        when:
        def type = post.getType()

        then:
        expectedResult == type

        where:
        message   | referencePostExists | expectedResult
        "message" | false          | PostType.ORIGINAL
        null      | true    | PostType.REPOST
        "message" | true    | PostType.QUOTE_POST

    }
}
