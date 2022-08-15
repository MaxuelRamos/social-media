package ramos.maxuel.socialmedia.mapper;

import org.mapstruct.Mapper;
import ramos.maxuel.socialmedia.controller.dto.PostDTO;
import ramos.maxuel.socialmedia.domain.Post;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
}
