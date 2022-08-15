package ramos.maxuel.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ramos.maxuel.socialmedia.controller.dto.PostDTO;
import ramos.maxuel.socialmedia.domain.Post;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(target = "referencePost.referencePost", ignore = true)
    PostDTO toDto(Post post);
}
