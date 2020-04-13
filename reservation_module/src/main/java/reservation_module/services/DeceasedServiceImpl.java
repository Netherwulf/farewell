package reservation_module.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import reservation_module.api.v1.mapper.DeceasedMapper;
import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.DeceasedListDTO;
import reservation_module.models.Deceased;
import reservation_module.models.Grave;
import reservation_module.repositories.DeceasedRepository;
import reservation_module.repositories.GraveRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeceasedServiceImpl implements DeceasedService {

    private final DeceasedMapper deceasedMapper;
    private final DeceasedRepository deceasedRepository;
    private final GraveRepository graveRepository;

    public DeceasedServiceImpl(DeceasedMapper deceasedMapper,
                               DeceasedRepository deceasedRepository,
                               GraveRepository graveRepository) {
        this.deceasedMapper = deceasedMapper;
        this.deceasedRepository = deceasedRepository;
        this.graveRepository = graveRepository;
    }


    @Override
    public DeceasedListDTO getAllDeceased() {
        List<DeceasedDTO> deceasedDTOs = deceasedRepository
                .findAll()
                .stream()
                .map(deceased ->{
                    DeceasedDTO deceasedDTO = deceasedMapper.deceasedToDeceasedDTO(deceased);
                    deceasedDTO.setGraveId(deceased.getId());
                    return deceasedDTO;
                })
                .collect(Collectors.toList());
        return new DeceasedListDTO(deceasedDTOs);
    }

    @Override
    public ResponseEntity getAllDeceasedByGraveId(Long graveId) {
        Optional<Grave> graveOptional = graveRepository.findById(graveId);

        if(!graveOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + graveId.toString());
        }

        Grave grave = graveOptional.get();

        List<DeceasedDTO> deceasedDTOs = grave
                .getDeceased()
                .stream()
                .map(deceased -> {
                    DeceasedDTO deceasedDTO = deceasedMapper.deceasedToDeceasedDTO(deceased);
                    deceasedDTO.setGraveId(graveId);
                    return deceasedDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(deceasedDTOs);
    }

    @Override
    public ResponseEntity getByDeceasedId(Long deceasedId) {
        Optional<Deceased> deceasedOptional = deceasedRepository.findById(deceasedId);

        if(!deceasedOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deceased not found for id: " + deceasedId.toString());
        }

        Deceased deceased = deceasedOptional.get();

        DeceasedDTO deceasedDTO = deceasedMapper.deceasedToDeceasedDTO(deceased);
        deceasedDTO.setGraveId(deceased.getGrave().getId());

        return ResponseEntity.status(HttpStatus.OK).body(deceasedDTO);
    }

    @Override
    public ResponseEntity saveAndReturnDTO(DeceasedDTO deceasedDTO) {
        Optional<Grave> graveOptional = graveRepository.findById(deceasedDTO.getGraveId());

        if(!graveOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + deceasedDTO.getGraveId().toString());
        } else {
            Grave grave = graveOptional.get();

            Optional<Deceased> deceasedOptional = grave
                    .getDeceased()
                    .stream()
                    .filter(deceased -> deceased.getId().equals(deceasedDTO.getId()))
                    .findFirst();

            if(deceasedOptional.isPresent()) {
                // update existing Deceased
                Deceased deceasedFound = deceasedOptional.get();
                deceasedFound.setSurname(deceasedDTO.getSurname());
                deceasedFound.setName(deceasedDTO.getName());
                deceasedFound.setDateOfBirth(deceasedDTO.getDateOfBirth());
                deceasedFound.setPlaceOfBirth(deceasedDTO.getPlaceOfBirth());
                deceasedFound.setDateOfDeath(deceasedDTO.getDateOfDeath());
                deceasedFound.setPlaceOfDeath(deceasedDTO.getPlaceOfDeath());
                deceasedFound.setGrave(grave);

            } else {
                // create new Deceased
                Deceased deceased = deceasedMapper.deceasedDTOToDeceased(deceasedDTO);
                deceased.setGrave(grave);
                grave.addDeceased(deceased);
            }
            Grave savedGrave = graveRepository.save(grave);

            Optional<Deceased> savedDeceasedOptional = savedGrave
                    .getDeceased()
                    .stream()
                    .filter(deceased -> deceased.getId().equals(deceasedDTO.getId()))
                    .findFirst();

            if(!savedDeceasedOptional.isPresent()) {
                // if Deceased is not found by id
                savedDeceasedOptional = savedGrave
                        .getDeceased()
                        .stream()
                        .filter(deceased -> deceased.getSurname().equals(deceasedDTO.getSurname()))
                        .filter(deceased -> deceased.getName().equals(deceasedDTO.getName()))
                        .filter(deceased -> deceased.getDateOfBirth().equals(deceasedDTO.getDateOfBirth()))
                        .filter(deceased -> deceased.getPlaceOfBirth().equals(deceasedDTO.getPlaceOfBirth()))
                        .filter(deceased -> deceased.getDateOfDeath().equals(deceasedDTO.getDateOfDeath()))
                        .filter(deceased -> deceased.getPlaceOfDeath().equals(deceasedDTO.getPlaceOfDeath()))
                        .findFirst();
            }

            Deceased savedDeceased = savedDeceasedOptional.get();
            DeceasedDTO savedDeceasedDTO = deceasedMapper.deceasedToDeceasedDTO(savedDeceased);
            savedDeceasedDTO.setGraveId(savedDeceased.getGrave().getId());

            return ResponseEntity.status(HttpStatus.OK).body(savedDeceasedDTO);
        }
    }

    @Override
    public ResponseEntity deleteByDeceasedId(Long deceasedId) {
        Optional<Deceased> deceasedOptional = deceasedRepository.findById(deceasedId);

        if (deceasedOptional.isPresent()) {
            Deceased deceased = deceasedOptional.get();

            Optional<Grave> graveOptional = graveRepository.findById(deceased.getGrave().getId());

            if (graveOptional.isPresent()) {
                Grave grave = graveOptional.get();
                Deceased deceasedToDelete = grave
                        .getDeceased()
                        .stream()
                        .filter(deceasedElem -> deceasedElem.getId().equals(deceasedId))
                        .findFirst()
                        .get();
                deceasedToDelete.setGrave(null);
                grave.getDeceased().removeIf(deceasedElem -> deceasedElem.getId().equals(deceasedId));
                graveRepository.save(grave);

                return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted deceased with id: " + deceasedId.toString());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grave not found for id: " + deceased.getGrave().getId().toString());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deceased not found for id: " + deceasedId.toString());
    }
}
