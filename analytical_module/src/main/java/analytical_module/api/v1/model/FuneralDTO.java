package analytical_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
