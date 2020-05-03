package farewell.authentication_module.api.v1.models.dtoModels;

import farewell.authentication_module.models.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserDTO {

    private long id;
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    private UserTypeDTO type;

    public UserDTO(String email, String firstName, String lastName, String password, UserTypeDTO type) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public UserTypeDTO getType() {
        return type;
    }
}
