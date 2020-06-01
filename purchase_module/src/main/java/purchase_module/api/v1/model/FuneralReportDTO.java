package purchase_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuneralReportDTO {
    // average time between reservation and purchase in days
    private Double averageReservationToPurchaseTime;

    // average number of funerals per day
    private Double averageFuneralsPerDay;

    // median of number of funerals per day
    private Double medianFuneralsPerDay;

    // mode of number of funerals per day
    private Long modeFuneralsPerDay;

    // average number of funerals per month
    private Double averageFuneralsPerMonth;

    // median of number of funerals per month
    private Double medianFuneralsPerMonth;

    // mode of number of funerals per month
    private Long modeFuneralsPerMonth;

    // average number of funerals per year
    private Double averageFuneralsPerYear;

    // median of number of funerals per year
    private Double medianFuneralsPerYear;

    // mode of number of funerals per year
    private Long modeFuneralsPerYear;

    // number of funerals per Funeral Director - HashMap
    private Map<String, Long> funeralsPerFuneralDirector;

    // average number of funerals per Funeral Director
    private Double averageFuneralsPerFuneralDirector;

    // median of number of funerals per Funeral Director
    private Double medianFuneralsPerFuneralDirector;

    // mode of number of funerals per Funeral Director
    private Long modeFuneralsPerFuneralDirector;

    // number of funerals per User - HashMap
    private Map<String, Long> funeralsPerUser;

    // average number of funerals per User
    private Double averageFuneralsPerUser;

    // median of number of funerals per User
    private Double medianFuneralsPerUser;

    // mode of number of funerals per User
    private Long modeFuneralsPerUser;
}
