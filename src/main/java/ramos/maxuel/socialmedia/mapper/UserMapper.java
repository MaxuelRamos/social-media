package ramos.maxuel.socialmedia.mapper;

import org.mapstruct.Mapper;
import ramos.maxuel.socialmedia.controller.dto.UserDTO;
import ramos.maxuel.socialmedia.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
}
