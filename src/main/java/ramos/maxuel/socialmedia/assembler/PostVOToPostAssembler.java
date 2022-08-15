package ramos.maxuel.socialmedia.assembler;

import org.springframework.stereotype.Component;
import ramos.maxuel.socialmedia.domain.Post;
import ramos.maxuel.socialmedia.domain.PostVO;

@Component
public class PostVOToPostAssembler {

    public Post assemble(PostVO vo) {
        Post post = vo.getPost();
        post.setAuthor(vo.getAuthor());
        post.setReferencePost(vo.getReferencePost());

        if (post.getReferencePost() != null) {
            post.getReferencePost().setAuthor(vo.getReferencePostAuthor());
        }

        return post;
    }
}
