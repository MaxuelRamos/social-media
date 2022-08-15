package ramos.maxuel.socialmedia.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ramos.maxuel.socialmedia.domain.PostType;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Long id;

    private String message;

    private ZonedDateTime timestamp;

    private Long referencePostId;

    private Long authorId;

    private PostDTO referencePost;

    private UserDTO author;

    private PostType type;

}
