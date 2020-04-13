package farewell.authentication_module.controllers;

import farewell.authentication_module.api.v1.models.dtoModels.LoginDTO;
import farewell.authentication_module.api.v1.models.dtoModels.ProfileDTO;
import farewell.authentication_module.api.v1.models.dtoModels.UserDTO;
import farewell.authentication_module.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private UserServiceInterface userService;

    @PostMapping({"/authenticate"})
    public ResponseEntity createAuthenticationToken(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @GetMapping({"/users"})
    public ResponseEntity<List<ProfileDTO>> users() {
        return userService.getAllUsers();
    }

    @PostMapping({"/login"})
    public ResponseEntity getToken(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
}
