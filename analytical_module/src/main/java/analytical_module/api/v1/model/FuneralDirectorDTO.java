package analytical_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FuneralDirectorDTO {
    @XmlElement
    private Long id;

    @XmlElement
    private String surname;
    @XmlElement
    private String name;
    @XmlElement
    private String dateOfBirth;
    @XmlElement
    private String religion;
    @XmlElement
    private String email;

//    private Set<FactDTO> facts = new HashSet<>();
}
