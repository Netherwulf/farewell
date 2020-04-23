package analytical_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraveReportDTO {
    // average time between reservation and purchase in days
    private Double averageReservationToPurchaseTime;

    // average number of graves per day
    private Double averageGravesPerDay;

    // median of number of graves per day
    private Double medianGravesPerDay;

    // mode of number of graves per day
    private Long modeGravesPerDay;

    // average number of graves per month
    private Double averageGravesPerMonth;

    // median of number of graves per month
    private Double medianGravesPerMonth;

    // mode of number of graves per month
    private Long modeGravesPerMonth;

    // average number of graves per year
    private Double averageGravesPerYear;

    // median of number of graves per year
    private Double medianGravesPerYear;

    // mode of number of graves per year
    private Long modeGravesPerYear;

    // number of deceased per Grave - HashMap
    private Map<String, Long> deceasedPerGrave;

    // average number of deceased per Grave
    private Double averageDeceasedPerGrave;

    // median of number of deceased per Grave
    private Double medianDeceasedPerGrave;

    // mode of number of deceased per Grave
    private Long modeDeceasedPerGrave;

    // number of graves per User - HashMap
    private Map<String, Long> gravesPerUser;

    // average number of graves per User
    private Double averageGravesPerUser;

    // median of number of graves per User
    private Double medianGravesPerUser;

    // mode of number of graves per User
    private Long modeGravesPerUser;
}
