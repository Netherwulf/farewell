package reservation_module.services;

import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.DeceasedListDTO;

public interface DeceasedService {

    DeceasedListDTO getAllDeceased();

    DeceasedListDTO getAllDeceasedByGraveId(Long graveId);

    DeceasedDTO getByGraveIdAndDeceasedId(Long graveId, Long deceasedId);

    DeceasedDTO saveAndReturnDTO(Long id, DeceasedDTO deceasedDTO);

    void deleteByGraveIdAndDeceasedId(Long graveId, Long deceasedId);
}
