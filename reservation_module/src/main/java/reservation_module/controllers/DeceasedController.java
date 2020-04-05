package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reservation_module.api.v1.model.DeceasedDTO;
import reservation_module.api.v1.model.DeceasedListDTO;
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
    @ResponseStatus(HttpStatus.OK)
    public DeceasedListDTO getAllDeceasedByGraveId(@PathVariable Long id) {
        return deceasedService.getAllDeceasedByGraveId(id);
    }


    @GetMapping("/graves/{graveId}/deceased/{deceasedId}")
    @ResponseStatus(HttpStatus.OK)
    public DeceasedDTO getByGraveIdAndDeceasedId(@PathVariable Long graveId, @PathVariable Long deceasedId) {
        return deceasedService.getByGraveIdAndDeceasedId(graveId, deceasedId);
    }

//    @PostMapping("/deceased")
//    @ResponseStatus(HttpStatus.CREATED)
//    public DeceasedDTO createNewDeceased(@RequestBody DeceasedDTO deceasedDTO) {
//        return deceasedService.createNewDeceased(deceasedDTO);
//    }
//
//    @PutMapping("/deceased/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public DeceasedDTO updateDeceased(@PathVariable Long id, @RequestBody DeceasedDTO deceasedDTO) {
//        return deceasedService.saveDeceasedByDTO(id, deceasedDTO);
//    }
//
//    @PatchMapping("/deceased/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public DeceasedDTO patchDeceased(@PathVariable Long id, @RequestBody DeceasedDTO deceasedDTO) {
//        return deceasedService.patchDeceased(id, deceasedDTO);
//    }

    @DeleteMapping("/graves/{graveId}/deceased/{deceasedId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByGraveIdAndDeceasedId(@PathVariable Long graveId, @PathVariable Long deceasedId) {
        deceasedService.deleteByGraveIdAndDeceasedId(graveId, deceasedId);
    }
}
