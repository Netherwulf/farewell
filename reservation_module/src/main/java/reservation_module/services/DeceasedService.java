package reservation_module.services;

import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.DeceasedListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public interface DeceasedService {

    DeceasedListDTO getAllDeceased();

    ResponseEntity getAllDeceasedByGraveId(Long graveId);

    ResponseEntity getByDeceasedId(Long deceasedId);

    ResponseEntity saveAndReturnDTO(DeceasedDTO deceasedDTO);

    ResponseEntity deleteByDeceasedId(Long deceasedId);
}
