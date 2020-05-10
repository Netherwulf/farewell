package reservation_module.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import reservation_module.api.v1.mapper.FuneralMapper;
import reservation_module.api.v1.mapper.GraveMapper;
import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.api.v1.model.FuneralDirectorListDTO;
import reservation_module.api.v1.model.FuneralListDTO;
import reservation_module.exceptions.ResourceNotFoundException;
import reservation_module.models.Deceased;
import reservation_module.models.Funeral;
import reservation_module.models.Grave;
import reservation_module.repositories.DeceasedRepository;
import reservation_module.repositories.FuneralRepository;
import reservation_module.repositories.GraveRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FuneralServiceImpl implements FuneralService {

    private final FuneralRepository funeralRepository;
    private final FuneralMapper funeralMapper;
    private final GraveRepository graveRepository;
    private final GraveMapper graveMapper;
    private final DeceasedRepository deceasedRepository;

    public FuneralServiceImpl(FuneralRepository funeralRepository, FuneralMapper funeralMapper, GraveRepository graveRepository, GraveMapper graveMapper, DeceasedRepository deceasedRepository) {
        this.funeralRepository = funeralRepository;
        this.funeralMapper = funeralMapper;
        this.graveRepository = graveRepository;
        this.graveMapper = graveMapper;
        this.deceasedRepository = deceasedRepository;
    }


    @Override
    public FuneralListDTO getAllFunerals() {
        final String uri = "http://analyticalModule:8081/funeralDirectors";

        RestTemplate restTemplate = new RestTemplate();
        FuneralDirectorListDTO result = restTemplate.getForObject(uri, FuneralDirectorListDTO.class, new HashMap<>());

        List<FuneralDTO> funeralDTOs = funeralRepository
                .findAll()
                .stream()
                .map(funeral -> {
                    FuneralDTO funeralDTO = funeralMapper.funeralToFuneralDTO(funeral);
                    if (funeralDTO.getGrave() != null) {
                        funeralDTO.getGrave().setFuneralId(funeralDTO.getId());
                        if (funeralDTO.getGrave().getDeceased() != null) {
                            for (DeceasedDTO deceasedDTO: funeralDTO.getGrave().getDeceased()) {
                                deceasedDTO.setGraveId(funeralDTO.getGrave().getId());
                            }
                        }
                    }
                    if (funeralDTO.getFuneralDirectorId() != null) {
                        funeralDTO.setFuneralDirector(result.getFuneralDirectors().get(Integer.parseInt(funeralDTO.getFuneralDirectorId())));
                    }
                    return funeralDTO;
                })
                .collect(Collectors.toList());

//        URI thirdPartyApi = new URI("http", null, "http://reservation_module", 8081, "/funeralDirectors", request.getQueryString(), null);
//
//        ResponseEntity<FuneralDirectorListDTO> resp =
//                restTemplate.exchange("http://analytical_module:8081/funeralDirectors", HttpMethod.GET, null, FuneralDirectorListDTO, null);
//
//        return resp.getBody();

        return new FuneralListDTO(funeralDTOs);
    }

    @Override
    public ResponseEntity getFuneralById(Long funeralId) {
        Optional<Funeral> funeralOptional = funeralRepository.findById(funeralId);

        final String uri = "http://analyticalModule:8081/funeralDirectors";

        RestTemplate restTemplate = new RestTemplate();
        FuneralDirectorListDTO result = restTemplate.getForObject(uri, FuneralDirectorListDTO.class, new HashMap<>());

        if (!funeralOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral not found for id: " + funeralId.toString());
        }

        FuneralDTO funeralDTO = funeralMapper.funeralToFuneralDTO(funeralOptional.get());

        if (funeralOptional.get().getGrave() != null) {
            funeralDTO.getGrave().setFuneralId(funeralDTO.getId());
            if (funeralDTO.getGrave().getDeceased() != null) {
                for (DeceasedDTO deceasedDTO: funeralDTO.getGrave().getDeceased()) {
                    deceasedDTO.setGraveId(funeralDTO.getGrave().getId());
                }
            }
        }
        if (funeralDTO.getFuneralDirectorId() != null) {
            funeralDTO.setFuneralDirector(result.getFuneralDirectors().get(Integer.parseInt(funeralDTO.getFuneralDirectorId())));
        }

        return ResponseEntity.status(HttpStatus.OK).body(funeralDTO);
    }

    @Override
    public FuneralDTO createNewFuneral(FuneralDTO funeralDTO) {
        return saveAndReturnDTO(funeralMapper.funeralDTOToFuneral(funeralDTO));
    }

    @Override
    public ResponseEntity saveFuneralByDTO(FuneralDTO funeralDTO) {
        Optional<Funeral> funeralOptional = funeralRepository.findById(funeralDTO.getId());

        if (!funeralOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral not found for id: " + funeralDTO.getId().toString());
        }

        Funeral funeral = funeralMapper.funeralDTOToFuneral(funeralDTO);

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

        funeralRepository.save(funeral);

        FuneralDTO returnDTO = funeralMapper.funeralToFuneralDTO(funeral);

        if (returnDTO.getGrave() != null) {
            returnDTO.getGrave().setFuneralId(returnDTO.getId());
            if (returnDTO.getGrave().getDeceased() != null) {
                for (DeceasedDTO deceasedDTO: returnDTO.getGrave().getDeceased()) {
                    deceasedDTO.setGraveId(returnDTO.getGrave().getId());
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnDTO);
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
    @Transactional
    public FuneralDTO saveAndReturnDTO(Funeral funeral) {
        Funeral savedFuneral = funeralRepository.save(funeral);

        if (funeral.getGrave() != null) {
            Grave grave = funeral.getGrave();
            grave.setFuneral(funeral);
            graveRepository.save(grave);
            if (!Objects.equals(grave.getDeceased(), new HashSet<>())) {
                for (Deceased deceased: grave.getDeceased()) {
                    deceased.setGrave(grave);
                    deceasedRepository.save(deceased);
                }
            }
        }

        FuneralDTO returnDTO = funeralMapper.funeralToFuneralDTO(savedFuneral);
        if (!Objects.equals(returnDTO.getGrave(), null)) {
            returnDTO.getGrave().setFuneralId(returnDTO.getId());
            if (!Objects.equals(returnDTO.getGrave().getDeceased(), new HashSet<>())) {
                for (DeceasedDTO deceased: returnDTO.getGrave().getDeceased()) {
                    deceased.setGraveId(returnDTO.getId());
                }
            }
        }

        return returnDTO;
    }

    @Override
    public ResponseEntity deleteFuneralById(Long id) {
        Optional<Funeral> funeralOptional = funeralRepository.findById(id);

        if (funeralOptional.isPresent()) {
            Funeral funeral = funeralOptional.get();
            if (funeral.getGrave() != null) {
                funeral.getGrave().setFuneral(null);
                funeral.setGrave(null);
            }
            funeralRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted funeral with id: " + id.toString());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral not found for id: " + id.toString());
        }
    }
}
