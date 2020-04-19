package farewell.authentication_module.api.v1.models.mappers;

import farewell.authentication_module.api.v1.models.dtoModels.ProfileDTO;
import farewell.authentication_module.api.v1.models.dtoModels.UserDTO;
import farewell.authentication_module.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    ProfileDTO userToProfileDTO(User user);
}
