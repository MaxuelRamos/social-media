package ramos.maxuel.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ramos.maxuel.socialmedia.controller.dto.UserProfileDTO;
import ramos.maxuel.socialmedia.domain.UserProfileVO;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.dateJoined", target = "dateJoined")
    UserProfileDTO toDto(UserProfileVO profile);
}
