package reservation_module.services;

import reservation_module.api.v1.model.GraveDTO;
import reservation_module.api.v1.model.GraveListDTO;
import reservation_module.models.Grave;

public interface GraveService {

    GraveListDTO getAllGraves();

    GraveDTO getGraveById(Long graveId);

    GraveDTO getGraveByFuneralId(Long funeralId);

    GraveDTO createNewGrave(GraveDTO graveDTO);

    GraveDTO saveGraveByDTO(Long id, GraveDTO graveDTO);

    GraveDTO patchGrave(Long id, GraveDTO graveDTO);

    GraveDTO saveAndReturnDTO(Grave grave);

    public void deleteGraveById(Long id);

}
