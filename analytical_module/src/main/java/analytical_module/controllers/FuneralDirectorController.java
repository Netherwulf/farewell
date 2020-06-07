package analytical_module.controllers;

import analytical_module.api.v1.model.FuneralDirectorDTO;
import analytical_module.api.v1.model.FuneralDirectorListDTO;
import analytical_module.services.FuneralDirectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class FuneralDirectorController {

    private final FuneralDirectorService funeralDirectorService;

    public FuneralDirectorController(FuneralDirectorService funeralDirectorService) {
        this.funeralDirectorService = funeralDirectorService;
    }

    @GetMapping(value="/funeralDirectors")
    FuneralDirectorListDTO getAllFuneralDirectors() {
        return funeralDirectorService.getAllFuneralDirectors();
    }

    @GetMapping("/funeralDirectors/{id}")
    ResponseEntity getFuneralDirectorById(@PathVariable Long id) {
        return funeralDirectorService.getFuneralDirectorById(id);
    }

    @GetMapping("/facts/{factId}/funeralDirectors")
    ResponseEntity getFuneralDirectorByFactId(@PathVariable Long factId) {
        return funeralDirectorService.getFuneralDirectorByFactId(factId);
    }

    @PostMapping("/funeralDirectors")
    FuneralDirectorDTO createNewFuneralDirector(@RequestBody FuneralDirectorDTO funeralDirectorDTO) {
        return funeralDirectorService.createNewFuneralDirector(funeralDirectorDTO);
    }

    @PutMapping("/funeralDirectors")
    ResponseEntity saveFuneralDirectorByDTO(@RequestBody FuneralDirectorDTO funeralDirectorDTO) {
        return funeralDirectorService.saveFuneralDirectorByDTO(funeralDirectorDTO);
    }

    @DeleteMapping("/funeralDirectors/{id}")
    ResponseEntity deleteFuneralDirectorById(@PathVariable Long id) {
        return funeralDirectorService.deleteFuneralDirectorById(id);
    }
}
