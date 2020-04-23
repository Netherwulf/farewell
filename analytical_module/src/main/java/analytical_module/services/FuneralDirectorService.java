package analytical_module.services;

import analytical_module.api.v1.model.FuneralDirectorDTO;
import analytical_module.api.v1.model.FuneralDirectorListDTO;
import analytical_module.models.FuneralDirector;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public interface FuneralDirectorService {
    FuneralDirectorListDTO getAllFuneralDirectors();

    ResponseEntity getFuneralDirectorById(Long funeralDirectorId);

    ResponseEntity getFuneralDirectorByFactId(Long factId);

    FuneralDirectorDTO createNewFuneralDirector(FuneralDirectorDTO funeralDirectorDTO);

    ResponseEntity saveFuneralDirectorByDTO(FuneralDirectorDTO funeralDirectorDTO);

    FuneralDirectorDTO saveAndReturnDTO(FuneralDirector funeralDirector);

    ResponseEntity deleteFuneralDirectorById(Long funeralDirectorId);
}
