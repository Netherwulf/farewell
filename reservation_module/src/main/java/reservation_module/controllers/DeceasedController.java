package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.DeceasedListDTO;
import reservation_module.api.v1.model.GraveDTO;
import reservation_module.services.DeceasedService;

@CrossOrigin
@RestController
public class DeceasedController {
    private final DeceasedService deceasedService;

    public DeceasedController(DeceasedService deceasedService) {
        this.deceasedService = deceasedService;
    }

    @GetMapping("/deceased")
    public DeceasedListDTO getAllDeceased() {
        return deceasedService.getAllDeceased();
    }

    @GetMapping("/graves/{id}/deceased")
    public ResponseEntity getAllDeceasedByGraveId(@PathVariable Long id) {
        return deceasedService.getAllDeceasedByGraveId(id);
    }

    @GetMapping("/deceased/{deceasedId}")
    public ResponseEntity getByDeceasedId(@PathVariable Long deceasedId) {
        return deceasedService.getByDeceasedId(deceasedId);
    }

    @PutMapping("/deceased")
    public ResponseEntity updateDeceased(@RequestBody DeceasedDTO deceasedDTO) {
        return deceasedService.saveAndReturnDTO(deceasedDTO);
    }

    @DeleteMapping("/deceased/{deceasedId}")
    public ResponseEntity deleteByGraveIdAndDeceasedId(@PathVariable Long deceasedId) {
        return deceasedService.deleteByDeceasedId(deceasedId);
    }
}
