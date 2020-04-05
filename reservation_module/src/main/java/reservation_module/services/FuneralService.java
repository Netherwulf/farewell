package reservation_module.services;

import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.api.v1.model.FuneralListDTO;
import reservation_module.models.Funeral;

public interface FuneralService {

    FuneralListDTO getAllFunerals();

    FuneralDTO getFuneralById(Long funeralId);

    FuneralDTO createNewFuneral(FuneralDTO funeralDTO);

    FuneralDTO saveFuneralByDTO(Long id, FuneralDTO funeralDTO);

    FuneralDTO patchFuneral(Long id, FuneralDTO funeralDTO);

    FuneralDTO saveAndReturnDTO(Funeral funeral);

    public void deleteFuneralById(Long id);
}
