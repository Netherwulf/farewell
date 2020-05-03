package farewell.authentication_module.api.v1.models.dtoModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    @NotBlank
    private long id;
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Enumerated
    private UserTypeDTO type;
    @NotBlank
    private Boolean active;
}
