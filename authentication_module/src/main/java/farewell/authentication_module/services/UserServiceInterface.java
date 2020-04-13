package farewell.authentication_module.services;

import farewell.authentication_module.api.v1.models.dtoModels.AuthenticationResponseDTO;
import farewell.authentication_module.api.v1.models.dtoModels.LoginDTO;
import farewell.authentication_module.api.v1.models.dtoModels.ProfileDTO;
import farewell.authentication_module.api.v1.models.dtoModels.UserDTO;
import farewell.authentication_module.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserServiceInterface {

    UserDTO saveAndReturnUserDTO(UserDTO userDTO);

    ResponseEntity<AuthenticationResponseDTO> register(UserDTO userDTO);

    ResponseEntity<AuthenticationResponseDTO> login(LoginDTO loginDTO);

    ResponseEntity<List<ProfileDTO>> getAllUsers();

    User getUserWithUsername(String username) throws UsernameNotFoundException;
}
