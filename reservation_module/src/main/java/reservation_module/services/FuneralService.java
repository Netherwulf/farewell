package reservation_module.services;

import org.springframework.http.ResponseEntity;

import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.api.v1.model.FuneralListDTO;
import reservation_module.models.Funeral;

public interface FuneralService {

    FuneralListDTO getAllFunerals();

    ResponseEntity getFuneralById(Long funeralId);

    FuneralDTO createNewFuneral(FuneralDTO funeralDTO);

    ResponseEntity saveFuneralByDTO(FuneralDTO funeralDTO);

    FuneralDTO patchFuneral(Long id, FuneralDTO funeralDTO);

    FuneralDTO saveAndReturnDTO(Funeral funeral);

    public ResponseEntity deleteFuneralById(Long id);
}
