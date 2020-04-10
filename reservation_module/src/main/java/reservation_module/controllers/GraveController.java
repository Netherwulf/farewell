package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import reservation_module.api.v1.model.GraveDTO;
import reservation_module.api.v1.model.GraveListDTO;
import reservation_module.services.GraveService;

@CrossOrigin
@RestController
public class GraveController {

    private final GraveService graveService;

    public GraveController(GraveService graveService) {
        this.graveService = graveService;
    }

    @GetMapping("/graves")
    public GraveListDTO getAllGraves() {
        return graveService.getAllGraves();
    }

    @GetMapping("/graves/{id}")
    public ResponseEntity getGraveById(@PathVariable Long id) {
        return graveService.getGraveById(id);
    }

    @GetMapping("/funerals/{id}/grave")
    public ResponseEntity getGraveByFuneralId(@PathVariable Long id) {
        return graveService.getGraveByFuneralId(id);
    }

    @GetMapping("/users/{id}/graves")
    public ResponseEntity getGravesByUserId(@PathVariable Long id) {
        return graveService.getGravesByUserId(id);
    }

    @PostMapping("/graves")
    @ResponseStatus(HttpStatus.CREATED)
    public GraveDTO createNewGrave(@RequestBody GraveDTO graveDTO) {
        return graveService.createNewGrave(graveDTO);
    }

    @PutMapping("/graves")
    public ResponseEntity updateGrave(@RequestBody GraveDTO graveDTO) {
        return graveService.saveGraveByDTO(graveDTO);
    }

    @PatchMapping("/graves")
    public ResponseEntity patchGrave(@RequestBody GraveDTO graveDTO) {
        return graveService.patchGrave(graveDTO);
    }

    @DeleteMapping("/graves/{id}")
    public ResponseEntity deleteGrave(@PathVariable Long id) {
        return graveService.deleteGraveById(id);
    }
}
