package reservation_module.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public DeceasedListDTO getAllDeceasedByGraveId(Long graveId) {
        Optional<Grave> graveOptional = graveRepository.findById(graveId);

        if(!graveOptional.isPresent()) {
            log.error("Grave id not found, id: " + graveId);
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

        return new DeceasedListDTO(deceasedDTOs);
    }

    @Override
    public DeceasedDTO getByGraveIdAndDeceasedId(Long graveId, Long deceasedId) {
        Optional<Grave> graveOptional = graveRepository.findById(graveId);

        if(!graveOptional.isPresent()) {
            log.error("Grave id not found, id: " + graveId);
        }

        Grave grave = graveOptional.get();

        Optional<Deceased> deceasedOptional = grave
                .getDeceased()
                .stream()
                .filter(deceased -> deceased.getId().equals(deceasedId))
                .findFirst();

        if(!deceasedOptional.isPresent()) {
            log.error("Deceased id not found, id: " + deceasedId);
        }

        Deceased deceased = deceasedOptional.get();

        DeceasedDTO deceasedDTO = deceasedMapper.deceasedToDeceasedDTO(deceased);
        deceasedDTO.setGraveId(graveId);

        return deceasedDTO;
    }

    @Override
    public DeceasedDTO saveAndReturnDTO(Long id, DeceasedDTO deceasedDTO) {
        Optional<Grave> graveOptional = graveRepository.findById(id);

        if(!graveOptional.isPresent()) {
            log.error("Grave not found for id: " + id);
            return new DeceasedDTO();
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

            return savedDeceasedDTO;
        }
    }

    @Override
    public void deleteByGraveIdAndDeceasedId(Long graveId, Long deceasedId) {
        Grave grave = graveRepository.findById(graveId).get();
        Deceased deceasedToDelete = grave
                .getDeceased()
                .stream()
                .filter(deceased -> deceased.getId().equals(deceasedId))
                .findFirst()
                .get();
        deceasedToDelete.setGrave(null);
        grave.getDeceased().removeIf(deceased -> deceased.getId().equals(deceasedId));
        graveRepository.save(grave);
    }
}
