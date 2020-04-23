package analytical_module.services;

import analytical_module.api.v1.model.FactDTO;
import analytical_module.api.v1.model.FactListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public interface FactService {
    FactListDTO getAllFacts();

    ResponseEntity getFactsByFuneralDirectorId(Long funeralDirectorId);

    ResponseEntity getFactById(Long factId);

    FactDTO createNewFact(FactDTO factDTO);

    FactDTO saveAndReturnDTO(FactDTO factDTO);

    ResponseEntity getFuneralReport();

    ResponseEntity getGraveReport();
}
