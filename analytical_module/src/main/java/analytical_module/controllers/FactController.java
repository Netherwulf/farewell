package analytical_module.controllers;

import analytical_module.api.v1.model.FactDTO;
import analytical_module.api.v1.model.FactListDTO;
import analytical_module.services.FactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class FactController {

    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping("/facts")
    public FactListDTO getAllFacts() {
        return factService.getAllFacts();
    }

    @GetMapping("/funeralDirectors/{funeralDirectorId}/facts")
    public ResponseEntity getFactsByFuneralDirectorId(@PathVariable Long funeralDirectorId) {
        return factService.getFactsByFuneralDirectorId(funeralDirectorId);
    }

    @GetMapping("/facts/{id}")
    public ResponseEntity getFactById(@PathVariable Long id) {
        return factService.getFactById(id);
    }

    @PostMapping("/facts")
    public FactDTO createNewFact(@RequestBody FactDTO factDTO) {
        return factService.createNewFact(factDTO);
    }

    @GetMapping("/funeralReport")
    public ResponseEntity getFuneralReport() {
        return factService.getFuneralReport();
    }

    @GetMapping(value="/graveReport", produces=MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity getGraveReport() {
        return factService.getGraveReport();
    }
}
