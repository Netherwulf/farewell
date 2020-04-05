package reservation_module.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reservation_module.api.v1.mapper.FuneralMapper;
import reservation_module.api.v1.mapper.GraveMapper;
import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.api.v1.model.FuneralListDTO;
import reservation_module.exceptions.ResourceNotFoundException;
import reservation_module.models.Funeral;
import reservation_module.models.Grave;
import reservation_module.repositories.FuneralRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FuneralServiceImpl implements FuneralService {

    private final FuneralRepository funeralRepository;
    private final FuneralMapper funeralMapper;
    private final GraveMapper graveMapper;

    public FuneralServiceImpl(FuneralRepository funeralRepository, FuneralMapper funeralMapper, GraveMapper graveMapper) {
        this.funeralRepository = funeralRepository;
        this.funeralMapper = funeralMapper;
        this.graveMapper = graveMapper;
    }


    @Override
    public FuneralListDTO getAllFunerals() {
        List<FuneralDTO> funeralDTOs = funeralRepository
                .findAll()
                .stream()
                .map(funeral -> {
                    FuneralDTO funeralDTO = funeralMapper.funeralToFuneralDTO(funeral);
                    if (funeralDTO.getGrave() != null) {
                        funeralDTO.getGrave().setFuneralId(funeralDTO.getId());
                    }
                    return funeralDTO;
                })
                .collect(Collectors.toList());
        return new FuneralListDTO(funeralDTOs);
    }

    @Override
    public FuneralDTO getFuneralById(Long funeralId) {
        Optional<Funeral> funeralOptional = funeralRepository.findById(funeralId);

        if(!funeralOptional.isPresent()) {
            log.error("Funeral not found for ID: " + funeralId.toString());
        }

        FuneralDTO funeralDTO = funeralMapper.funeralToFuneralDTO(funeralOptional.get());

        if (funeralOptional.get().getGrave() != null) {
            funeralDTO.getGrave().setFuneralId(funeralDTO.getId());
        }

        return funeralDTO;
    }

    @Override
    public FuneralDTO createNewFuneral(FuneralDTO funeralDTO) {
        return saveAndReturnDTO(funeralMapper.funeralDTOToFuneral(funeralDTO));
    }

    @Override
    @Transactional
    public FuneralDTO saveFuneralByDTO(Long id, FuneralDTO funeralDTO) {
        Funeral funeral = funeralMapper.funeralDTOToFuneral(funeralDTO);
        funeral.setId(id);

        return saveAndReturnDTO(funeral);
    }

    @Override
    public FuneralDTO patchFuneral(Long id, FuneralDTO funeralDTO) {
        return funeralRepository
                .findById(id)
                .map(funeral -> {
                    if (funeralDTO.getReservationDate() != null) {
                        funeral.setReservationDate(funeralDTO.getReservationDate());
                    }
                    if (funeralDTO.getDate() != null) {
                        funeral.setDate(funeralDTO.getDate());
                    }
                    if (funeralDTO.getFuneralDirectorId() != null) {
                        funeral.setFuneralDirectorId(funeralDTO.getFuneralDirectorId());
                    }
                    if (funeralDTO.getUserId() != null) {
                        funeral.setUserId(funeralDTO.getUserId());
                    }
                    if (funeralDTO.getGrave() != null) {
                        funeral.setGrave(graveMapper.graveDTOToGrave(funeralDTO.getGrave()));
                    }
                    return saveAndReturnDTO(funeral);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public FuneralDTO saveAndReturnDTO(Funeral funeral) {
        Funeral savedFuneral = funeralRepository.save(funeral);

        FuneralDTO returnDTO = funeralMapper.funeralToFuneralDTO(savedFuneral);

        return returnDTO;
    }

    @Override
    public void deleteFuneralById(Long id) {
        funeralRepository.deleteById(id);
    }
}
