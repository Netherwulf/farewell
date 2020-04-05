package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @ResponseStatus(HttpStatus.OK)
    public GraveDTO getGraveById(@PathVariable Long id) {
        return graveService.getGraveById(id);
    }

    @GetMapping("/funerals/{id}/grave")
    @ResponseStatus(HttpStatus.OK)
    public GraveDTO getGraveByFuneralId(@PathVariable Long id) {
        return graveService.getGraveByFuneralId(id);
    }

    @PostMapping("/graves")
    @ResponseStatus(HttpStatus.CREATED)
    public GraveDTO createNewGrave(@RequestBody GraveDTO graveDTO) {
        return graveService.createNewGrave(graveDTO);
    }

    @PutMapping("/graves/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GraveDTO updateGrave(@PathVariable Long id, @RequestBody GraveDTO graveDTO) {
        return graveService.saveGraveByDTO(id, graveDTO);
    }

    @PatchMapping("/graves/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GraveDTO patchGrave(@PathVariable Long id, @RequestBody GraveDTO graveDTO) {
        return graveService.patchGrave(id, graveDTO);
    }

    @DeleteMapping("/graves/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGrave(@PathVariable Long id) {
        graveService.deleteGraveById(id);
    }
}
