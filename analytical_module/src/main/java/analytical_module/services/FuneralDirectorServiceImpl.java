package analytical_module.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import analytical_module.api.v1.mapper.FactMapper;
import analytical_module.api.v1.mapper.FuneralDirectorMapper;
import analytical_module.api.v1.model.FactDTO;
import analytical_module.api.v1.model.FuneralDirectorDTO;
import analytical_module.api.v1.model.FuneralDirectorListDTO;
import analytical_module.models.Fact;
import analytical_module.models.FuneralDirector;
import analytical_module.repositories.FactRepository;
import analytical_module.repositories.FuneralDirectorRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FuneralDirectorServiceImpl implements FuneralDirectorService {
    private final FactMapper factMapper;
    private final FuneralDirectorMapper funeralDirectorMapper;
    private final FactRepository factRepository;
    private final FuneralDirectorRepository funeralDirectorRepository;

    public FuneralDirectorServiceImpl(FactMapper factMapper,
                                      FuneralDirectorMapper funeralDirectorMapper,
                                      FactRepository factRepository,
                                      FuneralDirectorRepository funeralDirectorRepository) {
        this.factMapper = factMapper;
        this.funeralDirectorMapper = funeralDirectorMapper;
        this.factRepository = factRepository;
        this.funeralDirectorRepository = funeralDirectorRepository;
    }

    @Override
    public FuneralDirectorListDTO getAllFuneralDirectors() {
        List<FuneralDirectorDTO> funeralDirectorDTOs = funeralDirectorRepository
                .findAll()
                .stream()
                .map(funeralDirector -> {
                    FuneralDirectorDTO funeralDirectorDTO = funeralDirectorMapper.funeralDirectorToFuneralDirectorDTO(funeralDirector);

                    if (!Objects.equals(funeralDirectorDTO.getFacts(), new HashSet<>())) {
                        for (FactDTO factDTO: funeralDirectorDTO.getFacts()) {
                            factDTO.setFuneralDirectorId(funeralDirectorDTO.getId());
                        }
                    }

                    return funeralDirectorDTO;
                })
                .collect(Collectors.toList());

        return new FuneralDirectorListDTO(funeralDirectorDTOs);
    }

    @Override
    public ResponseEntity getFuneralDirectorById(Long funeralDirectorId) {
        Optional<FuneralDirector> funeralDirectorOptional = funeralDirectorRepository.findById(funeralDirectorId);

        if (!funeralDirectorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral Director not found for ID: " + funeralDirectorId.toString());
        }

        FuneralDirector funeralDirector = funeralDirectorOptional.get();

        FuneralDirectorDTO funeralDirectorDTO = funeralDirectorMapper.funeralDirectorToFuneralDirectorDTO(funeralDirector);

        if (!Objects.equals(funeralDirectorDTO.getFacts(), new HashSet<>())) {
            for (FactDTO factDTO: funeralDirectorDTO.getFacts()) {
                factDTO.setFuneralDirectorId(funeralDirectorId);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(funeralDirectorDTO);
    }

    @Override
    public ResponseEntity getFuneralDirectorByFactId(Long factId) {
        Optional<Fact> factOptional = factRepository.findById(factId);

        if (!factOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fact not found for ID: " + factId.toString());
        }

        Fact fact = factOptional.get();

        if (fact.getFuneralDirector() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fact with ID: " + factId.toString() + " does not have a Funeral Director");
        }

        FuneralDirector funeralDirector = fact.getFuneralDirector();

        FuneralDirectorDTO funeralDirectorDTO = funeralDirectorMapper.funeralDirectorToFuneralDirectorDTO(funeralDirector);

        if (!Objects.equals(funeralDirectorDTO.getFacts(), new HashSet<>())) {
            for (FactDTO factDTO: funeralDirectorDTO.getFacts()) {
                factDTO.setFuneralDirectorId(funeralDirectorDTO.getId());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(funeralDirectorDTO);
    }

    @Override
    public FuneralDirectorDTO createNewFuneralDirector(FuneralDirectorDTO funeralDirectorDTO) {
        return saveAndReturnDTO(funeralDirectorMapper.funeralDirectorDTOToFuneralDirector(funeralDirectorDTO));
    }

    @Override
    public ResponseEntity saveFuneralDirectorByDTO(FuneralDirectorDTO funeralDirectorDTO) {
        Optional<FuneralDirector> funeralDirectorOptional = funeralDirectorRepository.findById(funeralDirectorDTO.getId());

        if (!funeralDirectorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral Director not found for ID: " + funeralDirectorId.toString());
        }

        FuneralDirector funeralDirector = funeralDirectorOptional.get();

        funeralDirector.setSurname(funeralDirectorDTO.getSurname());
        funeralDirector.setName(funeralDirectorDTO.getName());
        funeralDirector.setDateOfBirth(funeralDirectorDTO.getDateOfBirth());
        funeralDirector.setReligion(funeralDirectorDTO.getReligion());
        funeralDirector.setEmail(funeralDirectorDTO.getEmail());

        FuneralDirector savedFuneralDirector = funeralDirectorRepository.save(funeralDirector);

        FuneralDirectorDTO returnDTO = funeralDirectorMapper.funeralDirectorToFuneralDirectorDTO(savedFuneralDirector);

        if (!Objects.equals(returnDTO.getFacts(), new HashSet<>())) {
            for (FactDTO factDTO: returnDTO.getFacts()) {
                factDTO.setFuneralDirectorId(returnDTO.getId());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnDTO);
    }

    @Override
    @Transactional
    public FuneralDirectorDTO saveAndReturnDTO(FuneralDirector funeralDirector) {
        FuneralDirector savedFuneralDirector = funeralDirectorRepository.save(funeralDirector);

        FuneralDirectorDTO returnDTO = funeralDirectorMapper.funeralDirectorToFuneralDirectorDTO(savedFuneralDirector);

        return returnDTO;
    }

    @Override
    public ResponseEntity deleteFuneralDirectorById(Long funeralDirectorId) {
        Optional<FuneralDirector> funeralDirectorOptional = funeralDirectorRepository.findById(funeralDirectorDTO.getId());

        if (!funeralDirectorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral Director not found for ID: " + funeralDirectorId.toString());
        }

        FuneralDirector funeralDirector = funeralDirectorOptional.get();

        if (!Objects.equals(funeralDirector.getFacts(), new HashSet<>())) {
            for (Fact fact: funeralDirector.getFacts()) {
                fact.setFuneralDirector(null);
                factRepository.save(fact);
            }
        }

        funeralDirector.setFacts(null);
        funeralDirectorRepository.deleteById(funeralDirectorId);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Funeral Director with id: " + funeralDirectorId.toString());
    }
}
