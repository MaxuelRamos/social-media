package ramos.maxuel.socialmedia.domain;

public interface PostVO {

    Post getPost();

    Post getReferencePost();

    User getAuthor();

    User getReferencePostAuthor();
}
