package analytical_module.api.v1.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GraveReportDTO {
    // average time between reservation and purchase in days
    @XmlElement
    private Double averageReservationToPurchaseTime;

    // average number of graves per day
    @XmlElement
    private Double averageGravesPerDay;

    // median of number of graves per day
    @XmlElement
    private Double medianGravesPerDay;

    // mode of number of graves per day
    @XmlElement
    private Long modeGravesPerDay;

    // average number of graves per month
    @XmlElement
    private Double averageGravesPerMonth;

    // median of number of graves per month
    @XmlElement
    private Double medianGravesPerMonth;

    // mode of number of graves per month
    @XmlElement
    private Long modeGravesPerMonth;

    // average number of graves per year
    @XmlElement
    private Double averageGravesPerYear;

    // median of number of graves per year
    @XmlElement
    private Double medianGravesPerYear;

    // mode of number of graves per year
    @XmlElement
    private Long modeGravesPerYear;

    // number of deceased per Grave - HashMap
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement
    private ArrayList<Long> deceasedPerGrave;

    // average number of deceased per Grave
    @XmlElement
    private Double averageDeceasedPerGrave;

    // median of number of deceased per Grave
    @XmlElement
    private Double medianDeceasedPerGrave;

    // mode of number of deceased per Grave
    @XmlElement
    private Long modeDeceasedPerGrave;

    // number of graves per User - HashMap
//    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement
    private ArrayList<Long> gravesPerUser;

    // average number of graves per User
    @XmlElement
    private Double averageGravesPerUser;

    // median of number of graves per User
    @XmlElement
    private Double medianGravesPerUser;

    // mode of number of graves per User
    @XmlElement
    private Long modeGravesPerUser;

}
