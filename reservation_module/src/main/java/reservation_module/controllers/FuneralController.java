package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.api.v1.model.FuneralListDTO;
import reservation_module.services.FuneralService;

@CrossOrigin
@RestController
//@RequestMapping(FuneralController.BASE_URL)
public class FuneralController {
//    public static final String BASE_URL = "/api/v1/funerals";

    private final FuneralService funeralService;

    public FuneralController(FuneralService funeralService) {
        this.funeralService = funeralService;
    }

    @GetMapping("/funerals")
    public FuneralListDTO getAllFunerals() {
        return funeralService.getAllFunerals();
    }

    @GetMapping("/funerals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuneralDTO getFuneralById(@PathVariable Long id) {
        return funeralService.getFuneralById(id);
    }

    @PostMapping("/funerals")
    @ResponseStatus(HttpStatus.CREATED)
    public FuneralDTO createNewFuneral(@RequestBody FuneralDTO funeralDTO) {
        return funeralService.createNewFuneral(funeralDTO);
    }

    @PutMapping("/funerals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuneralDTO updateFuneral(@PathVariable Long id, @RequestBody FuneralDTO funeralDTO) {
        return funeralService.saveFuneralByDTO(id, funeralDTO);
    }

    @PatchMapping("/funerals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuneralDTO patchFuneral(@PathVariable Long id, @RequestBody FuneralDTO funeralDTO) {
        return funeralService.patchFuneral(id, funeralDTO);
    }

    @DeleteMapping("/funerals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFuneral(@PathVariable Long id) {
        funeralService.deleteFuneralById(id);
    }

}