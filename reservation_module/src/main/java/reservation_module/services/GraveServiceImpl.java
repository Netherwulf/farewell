package reservation_module.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reservation_module.api.v1.mapper.DeceasedMapper;
import reservation_module.api.v1.mapper.FuneralMapper;
import reservation_module.api.v1.mapper.GraveMapper;
import reservation_module.api.v1.model.GraveDTO;
import reservation_module.api.v1.model.GraveListDTO;
import reservation_module.exceptions.ResourceNotFoundException;
import reservation_module.models.Deceased;
import reservation_module.models.Funeral;
import reservation_module.models.Grave;
import reservation_module.repositories.FuneralRepository;
import reservation_module.repositories.GraveRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GraveServiceImpl implements GraveService {

    private final GraveMapper graveMapper;
    private final FuneralMapper funeralMapper;
    private final DeceasedMapper deceasedMapper;
    private final GraveRepository graveRepository;
    private final FuneralRepository funeralRepository;

    public GraveServiceImpl(GraveMapper graveMapper,
                            FuneralMapper funeralMapper,
                            DeceasedMapper deceasedMapper,
                            GraveRepository graveRepository,
                            FuneralRepository funeralRepository) {
        this.graveMapper = graveMapper;
        this.funeralMapper = funeralMapper;
        this.deceasedMapper = deceasedMapper;
        this.graveRepository = graveRepository;
        this.funeralRepository = funeralRepository;
    }


    @Override
    public GraveListDTO getAllGraves() {
        List<GraveDTO> graveDTOs = graveRepository
                .findAll()
                .stream()
                .map(grave -> {
                    GraveDTO graveDTO = graveMapper.graveToGraveDTO(grave);
                    graveDTO.setFuneralId(grave.getFuneral().getId());
                    return graveDTO;
                })
                .collect(Collectors.toList());
        return new GraveListDTO(graveDTOs);
    }

    @Override
    public GraveDTO getGraveById(Long graveId) {
        Optional<Grave> graveOptional = graveRepository.findById(graveId);

        if (!graveOptional.isPresent()) {
           System.out.println("Grave not found for ID value: " + graveId.toString());
        }

        GraveDTO graveDTO = graveMapper.graveToGraveDTO(graveOptional.get());

        graveDTO.setFuneralId(graveOptional.get().getFuneral().getId());

        return graveDTO;
    }

    @Override
    public GraveDTO getGraveByFuneralId(Long funeralId) {
        Optional<Funeral> funeralOptional = funeralRepository.findById(funeralId);

        if (!funeralOptional.isPresent()) {
            log.error("Funeral not found for ID: " + funeralId.toString());
        }

        Funeral funeral = funeralOptional.get();

        Grave grave = funeral.getGrave();

        GraveDTO graveDTO = graveMapper.graveToGraveDTO(grave);

        graveDTO.setFuneralId(grave.getFuneral().getId());

        return graveDTO;
    }

    @Override
    public GraveDTO createNewGrave(GraveDTO graveDTO) {
        return saveAndReturnDTO(graveMapper.graveDTOToGrave(graveDTO));
    }

    @Override
    public GraveDTO saveGraveByDTO(Long id, GraveDTO graveDTO) {
        Grave grave = graveMapper.graveDTOToGrave(graveDTO);
        grave.setId(id);

        return saveAndReturnDTO(grave);
    }

    @Override
    public GraveDTO patchGrave(Long id, GraveDTO graveDTO) {
        return graveRepository
                .findById(id)
                .map(grave -> {
                    if (graveDTO.getCapacity() != null) {
                        grave.setCapacity(graveDTO.getCapacity());
                    }
                    if (graveDTO.getCoordinates() != null) {
                        grave.setCoordinates(graveDTO.getCoordinates());
                    }
                    if (graveDTO.getReservationDate() != null) {
                        grave.setReservationDate(graveDTO.getReservationDate());
                    }
                    if (graveDTO.getGraveNumber() != null) {
                        grave.setGraveNumber(graveDTO.getGraveNumber());
                    }
                    if (graveDTO.getDeceased() != null) {
                        Set<Deceased> deceasedSet = graveDTO
                                .getDeceased()
                                .stream()
                                .map(deceasedDTO -> {
                                    Deceased deceased = deceasedMapper.deceasedDTOToDeceased(deceasedDTO);
                                    return deceased;
                                })
                                .collect(Collectors.toSet());
                        grave.setDeceased(deceasedSet);
                    }
                    if (graveDTO.getFuneralId() != null) {
                        grave.setFuneral(funeralRepository.findById(graveDTO.getFuneralId()).get());
                    }
                    return saveAndReturnDTO(grave);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public GraveDTO saveAndReturnDTO(Grave grave) {
        Grave savedGrave = graveRepository.save(grave);

        GraveDTO returnDTO = graveMapper.graveToGraveDTO(savedGrave);

        returnDTO.setFuneralId(grave.getFuneral().getId());

        return returnDTO;
    }

    @Override
    public void deleteGraveById(Long id) {
        graveRepository.deleteById(id);
    }
}
