package farewell.authentication_module.services;

import com.oracle.tools.packager.Log;
import farewell.authentication_module.api.v1.models.dtoModels.ProfileDTO;
import farewell.authentication_module.repositories.UsersRepository;
import farewell.authentication_module.api.v1.models.dtoModels.AuthenticationResponseDTO;
import farewell.authentication_module.api.v1.models.dtoModels.LoginDTO;
import farewell.authentication_module.api.v1.models.dtoModels.UserDTO;
import farewell.authentication_module.api.v1.models.mappers.UserMapper;
import farewell.authentication_module.models.ErrorResponse;
import farewell.authentication_module.models.User;
import farewell.authentication_module.utils.EmailValidator;
import farewell.authentication_module.utils.JWTUtils;
import farewell.authentication_module.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    static int INCORRECT_ARGUMENT_CODE = 403;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public UserDTO saveAndReturnUserDTO(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        User sameEmail = usersRepository.findByEmail(userDTO.getEmail());
        if (sameEmail == null) {
            User savedUser = usersRepository.save(user);
            UserDTO savedUserDTO = userMapper.userToUserDTO(savedUser);
            return savedUserDTO;
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity register(UserDTO userDTO) {
        if (!EmailValidator.isValid(userDTO.getEmail())) {
            return ResponseEntity.status(INCORRECT_ARGUMENT_CODE).body(new ErrorResponse("Incorrect email", INCORRECT_ARGUMENT_CODE));
        }

        if (!PasswordValidator.isValid(userDTO.getPassword())) {
            return ResponseEntity.status(INCORRECT_ARGUMENT_CODE).body(new ErrorResponse("Password should be between 8 and 20 characters and contains at least one number.", INCORRECT_ARGUMENT_CODE));
        }

        UserDTO saved = saveAndReturnUserDTO(userDTO);

        if (saved == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User with email: "+ userDTO.getEmail() +" already exist", HttpStatus.BAD_REQUEST.value()));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());
        String jwt = jwtUtils.generateToken(userDetails);
        AuthenticationResponseDTO dto = new AuthenticationResponseDTO(jwt);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity login(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
            String jwt = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Incorrect email or password", HttpStatus.UNAUTHORIZED.value()));
        }
    }

    @Override
    public ResponseEntity<List<ProfileDTO>> getAllUsers() {
        List<ProfileDTO> users = usersRepository
                .findAll()
                .stream()
                .map( user -> {
                   return userMapper.userToProfileDTO(user);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public User getUserWithUsername(String username) {
        User user = usersRepository.findByEmail(username);
        return user;
    }
}
