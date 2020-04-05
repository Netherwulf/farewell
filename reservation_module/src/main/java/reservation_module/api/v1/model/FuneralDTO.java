package reservation_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuneralDTO {
    private Long id;

    @NotBlank
    private String reservationDate;

    @NotBlank
    private String date;

    @NotBlank
    private String funeralDirectorId;

    @NotBlank
    private String userId;

    private GraveDTO grave;
}
