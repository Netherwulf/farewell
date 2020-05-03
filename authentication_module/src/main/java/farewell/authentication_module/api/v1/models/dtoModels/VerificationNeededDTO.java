package farewell.authentication_module.api.v1.models.dtoModels;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerificationNeededDTO {

    @NotBlank
    private String message;

    public VerificationNeededDTO(@NotBlank String message) {
        this.message = message;
    }
}