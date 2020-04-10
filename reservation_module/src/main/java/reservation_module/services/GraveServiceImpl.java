package reservation_module.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import reservation_module.api.v1.mapper.DeceasedMapper;
import reservation_module.api.v1.mapper.FuneralMapper;
import reservation_module.api.v1.mapper.GraveMapper;
import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.GraveDTO;
import reservation_module.api.v1.model.GraveListDTO;
import reservation_module.exceptions.NotFoundException;
import reservation_module.exceptions.ResourceNotFoundException;
import reservation_module.models.Deceased;
import reservation_module.models.Funeral;
import reservation_module.models.Grave;
import reservation_module.repositories.DeceasedRepository;
import reservation_module.repositories.FuneralRepository;
import reservation_module.repositories.GraveRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GraveServiceImpl implements GraveService {

    private final GraveMapper graveMapper;
    private final FuneralMapper funeralMapper;
    private final DeceasedMapper deceasedMapper;
    private final GraveRepository graveRepository;
    private final FuneralRepository funeralRepository;
    private final DeceasedRepository deceasedRepository;

    public GraveServiceImpl(GraveMapper graveMapper,
                            FuneralMapper funeralMapper,
                            DeceasedMapper deceasedMapper,
                            GraveRepository graveRepository,
                            FuneralRepository funeralRepository,
                            DeceasedRepository deceasedRepository) {
        this.graveMapper = graveMapper;
        this.funeralMapper = funeralMapper;
        this.deceasedMapper = deceasedMapper;
        this.graveRepository = graveRepository;
        this.funeralRepository = funeralRepository;
        this.deceasedRepository = deceasedRepository;
    }


    @Override
    public GraveListDTO getAllGraves() {
        List<GraveDTO> graveDTOs = graveRepository
                .findAll()
                .stream()
                .map(grave -> {
                    GraveDTO graveDTO = graveMapper.graveToGraveDTO(grave);
                    if (grave.getFuneral() != null) {
                        graveDTO.setFuneralId(grave.getFuneral().getId());
                    }
                    if (!Objects.equals(graveDTO.getDeceased(), new HashSet<>())) {
                        for (DeceasedDTO deceased: graveDTO.getDeceased()) {
                            deceased.setGraveId(graveDTO.getId());
                        }
                    }
                    return graveDTO;
                })
                .collect(Collectors.toList());
        return new GraveListDTO(graveDTOs);
    }

    @Override
    public ResponseEntity getGraveById(Long graveId) {
        Optional<Grave> graveOptional = graveRepository.findById(graveId);

        if (!graveOptional.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + graveId.toString());
        }

        GraveDTO graveDTO = graveMapper.graveToGraveDTO(graveOptional.get());

        if (!Objects.equals(graveDTO.getDeceased(), new HashSet<>())) {
            for (DeceasedDTO deceased: graveDTO.getDeceased()) {
                deceased.setGraveId(graveDTO.getId());
            }
        }

        if (graveOptional.get().getFuneral() != null) {
            graveDTO.setFuneralId(graveOptional.get().getFuneral().getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(graveDTO);
    }

    @Override
    public ResponseEntity getGraveByFuneralId(Long funeralId) {
        Optional<Funeral> funeralOptional = funeralRepository.findById(funeralId);

        if (!funeralOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral not found for ID: " + funeralId.toString());
        }

        Funeral funeral = funeralOptional.get();

        if (funeral.getGrave() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral with ID: " + funeralId.toString() + " does not have a Grave.");
        }

        Grave grave = funeral.getGrave();

        GraveDTO graveDTO = graveMapper.graveToGraveDTO(grave);

        if (!Objects.equals(graveDTO.getDeceased(), new HashSet<>())) {
            for (DeceasedDTO deceased: graveDTO.getDeceased()) {
                deceased.setGraveId(graveDTO.getId());
            }
        }
        if (grave.getFuneral() != null) {
            graveDTO.setFuneralId(grave.getFuneral().getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(graveDTO);
    }

    @Override
    public ResponseEntity getGravesByUserId(Long userId) {
        List<GraveDTO> graveDTOs = graveRepository
                .findAll()
                .stream()
                .filter(grave -> grave.getUserId() == userId)
                .map(grave -> {
                    GraveDTO graveDTO = graveMapper.graveToGraveDTO(grave);
                    if (grave.getFuneral() != null) {
                        graveDTO.setFuneralId(grave.getFuneral().getId());
                    }
                    if (!Objects.equals(graveDTO.getDeceased(), new HashSet<>())) {
                        for (DeceasedDTO deceased: graveDTO.getDeceased()) {
                            deceased.setGraveId(graveDTO.getId());
                        }
                    }
                    return graveDTO;
                })
                .collect(Collectors.toList());
        if (graveDTOs.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No graves found for user ID: " + userId.toString());
        }
//        return new GraveListDTO(graveDTOs);
        return ResponseEntity.status(HttpStatus.OK).body(new GraveListDTO(graveDTOs));
    }

    @Override
    public GraveDTO createNewGrave(GraveDTO graveDTO) {
        return saveAndReturnDTO(graveMapper.graveDTOToGrave(graveDTO));
    }

    @Override
    public ResponseEntity saveGraveByDTO(GraveDTO graveDTO) {
        if (graveDTO.getFuneralId() != null) {
            Optional<Funeral> funeralOptional = funeralRepository.findById(graveDTO.getFuneralId());

            if (!funeralOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral not found for id: " + graveDTO.getFuneralId().toString());
            }
        }

        Optional<Grave> graveOptional = graveRepository.findById(graveDTO.getId());

        if (!graveOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + graveDTO.getId().toString());
        }

        Grave grave = graveOptional.get();

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

        if (grave.getFuneral().getId() != graveDTO.getFuneralId()) {
            Optional<Funeral> previousFuneralOptional = funeralRepository.findById(grave.getFuneral().getId());

            Funeral previousFuneral = previousFuneralOptional.get();

            previousFuneral.setGrave(null);

            funeralRepository.save(previousFuneral);

            Optional<Funeral> funeralOptional = funeralRepository.findById(graveDTO.getFuneralId());

            Funeral newFuneral = funeralOptional.get();

            newFuneral.setGrave(grave);

            grave.setFuneral(newFuneral);

            funeralRepository.save(newFuneral);
        }

        GraveDTO returnDTO = graveMapper.graveToGraveDTO(grave);

        if (!Objects.equals(returnDTO.getDeceased(), new HashSet<>())) {
            for (DeceasedDTO deceased: returnDTO.getDeceased()) {
                deceased.setGraveId(returnDTO.getId());
            }
        }
        if (grave.getFuneral() != null) {
            returnDTO.setFuneralId(grave.getFuneral().getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnDTO);
    }

    @Override
    public ResponseEntity patchGrave(GraveDTO graveDTO) {
        Optional<Grave> graveOptional = graveRepository.findById(graveDTO.getId());

        if (!graveOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + graveDTO.getId().toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(graveRepository
                .findById(graveDTO.getId())
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
                }));
    }

    @Override
    @Transactional
    public GraveDTO saveAndReturnDTO(Grave grave) {
        Grave savedGrave = graveRepository.save(grave);

        if (!Objects.equals(grave.getDeceased(), new HashSet<>())) {
            for (Deceased deceased: grave.getDeceased()) {
                deceased.setGrave(grave);
                deceasedRepository.save(deceased);
            }
        }

        GraveDTO returnDTO = graveMapper.graveToGraveDTO(savedGrave);

        if (!Objects.equals(returnDTO.getDeceased(), new HashSet<>())) {
            for (DeceasedDTO deceased: returnDTO.getDeceased()) {
                deceased.setGraveId(returnDTO.getId());
            }
        }
        if (grave.getFuneral() != null) {
            returnDTO.setFuneralId(grave.getFuneral().getId());
        }

        return returnDTO;
    }

    @Override
    public ResponseEntity deleteGraveById(Long id) {
        Optional<Grave> graveOptional = graveRepository.findById(id);

        if (graveOptional.isPresent()) {
            Grave grave = graveOptional.get();
            if (grave.getFuneral() != null) {
                grave.getFuneral().setGrave(null);
                grave.setFuneral(null);
            }
            graveRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted grave with id: " + id.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + id.toString());
        }
    }
}
