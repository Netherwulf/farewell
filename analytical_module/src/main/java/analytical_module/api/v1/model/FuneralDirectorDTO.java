package analytical_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuneralDirectorDTO {
    private Long id;

    private String surname;
    private String name;
    private String dateOfBirth;
    private String religion;
    private String email;

    private Set<FactDTO> facts = new HashSet<>();
}
