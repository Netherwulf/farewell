package analytical_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeceasedDTO {
    private Long id;

    @NotBlank
    private String surname;

    @NotBlank
    private String name;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String placeOfBirth;

    @NotBlank
    private String dateOfDeath;

    @NotBlank
    private String placeOfDeath;

    private Long graveId;
}
