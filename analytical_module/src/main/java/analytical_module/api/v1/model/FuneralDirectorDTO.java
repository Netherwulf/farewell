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
public class FuneralDirectorDTO {
    private Long id;

    private String surname;
    private String name;
    private String dateOfBirth;
    private String religion;
    private String email;

//    private Set<FactDTO> facts = new HashSet<>();
}
