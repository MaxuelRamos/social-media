package ramos.maxuel.socialmedia.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ramos.maxuel.socialmedia.domain.PostType;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String message;

    private ZonedDateTime timestamp;

    private PostDTO referencePost;

    private PostType type;

}
