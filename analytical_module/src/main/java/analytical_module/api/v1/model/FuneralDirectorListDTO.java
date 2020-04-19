package analytical_module.api.v1.model;

import analytical_module.models.FuneralDirector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuneralDirectorListDTO {
    List<FuneralDirector> funeralDirectors;
}
