package ramos.maxuel.socialmedia.domain;

import lombok.*;
import ramos.maxuel.socialmedia.controller.dto.PostDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@Entity
//@Table(name = "Movie")
public class Post {

    private String message;

    private ZonedDateTime timestamp;

    private Post referencePost;

    public PostType getPostType() {
        return referencePost == null ? PostType.ORIGINAL :
                message == null ? PostType.REPOST :
                        PostType.QUOTE_POST;
    }
}
