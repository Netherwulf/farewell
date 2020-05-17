package farewell.authentication_module.api.v1.models.dtoModels;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationResponseDTO {

    @NotBlank
    private String authenticationToken;

    @NotBlank Long userId;

    public AuthenticationResponseDTO(@NotBlank String authenticationToken, @NotBlank Long userId) {
        this.authenticationToken = authenticationToken;
        this.userId = userId;
    }
}
