package analytical_module.api.v1.model;

import analytical_module.models.Fact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactListDTO {
    List<Fact> facts;
}
