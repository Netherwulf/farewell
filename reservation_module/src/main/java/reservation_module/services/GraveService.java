package reservation_module.services;

import reservation_module.api.v1.model.GraveDTO;
import reservation_module.api.v1.model.GraveListDTO;
import reservation_module.models.Grave;

import org.springframework.http.ResponseEntity;

public interface GraveService {

    GraveListDTO getAllGraves();

    ResponseEntity getGraveById(Long graveId);

    ResponseEntity getGraveByFuneralId(Long funeralId);

    ResponseEntity getGravesByUserId(Long userId);

    GraveDTO createNewGrave(GraveDTO graveDTO);

    ResponseEntity saveGraveByDTO(GraveDTO graveDTO);

    ResponseEntity patchGrave(GraveDTO graveDTO);

    GraveDTO saveAndReturnDTO(Grave grave, Long userId);

    public ResponseEntity deleteGraveById(Long id);

}
