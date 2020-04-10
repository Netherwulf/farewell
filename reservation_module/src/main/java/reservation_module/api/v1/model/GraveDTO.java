package reservation_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraveDTO {
    private Long id;

    @NotBlank
    private String reservationDate;

    @NotBlank
    private String graveNumber;

    @NotBlank
    private String coordinates;

    @NotBlank
    private String capacity;

    private Long funeralId;

    private Long userId;

    private Set<DeceasedDTO> deceased = new HashSet<>();
}
