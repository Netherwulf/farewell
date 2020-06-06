package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import reservation_module.api.v1.model.FuneralDTO;
import reservation_module.api.v1.model.FuneralListDTO;
import reservation_module.services.FuneralService;
import reservation_module.services.RabbitMQSender;

import java.io.IOException;

@CrossOrigin
@RestController
//@RequestMapping(FuneralController.BASE_URL)
public class FuneralController {
//    public static final String BASE_URL = "/api/v1/funerals";

    private final FuneralService funeralService;

    private final RabbitMQSender rabbitMQSender;

    public FuneralController(FuneralService funeralService, RabbitMQSender rabbitMQSender) {
        this.funeralService = funeralService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping(value="/funerals", produces=MediaType.APPLICATION_XML_VALUE)
    public FuneralListDTO getAllFunerals() {
        return funeralService.getAllFunerals();
    }

    @GetMapping("/funerals/{id}")
    public ResponseEntity getFuneralById(@PathVariable Long id) {
        return funeralService.getFuneralById(id);
    }

    @PostMapping("/funerals")
    @ResponseStatus(HttpStatus.CREATED)
    public FuneralDTO createNewFuneral(@RequestBody FuneralDTO funeralDTO) throws IOException {
        FuneralDTO returnDTO = funeralService.createNewFuneral(funeralDTO);
        rabbitMQSender.send(returnDTO);
        return returnDTO;
    }

    @PutMapping("/funerals")
    public ResponseEntity updateFuneral(@RequestBody FuneralDTO funeralDTO) {
        return funeralService.saveFuneralByDTO(funeralDTO);
    }

    @PatchMapping("/funerals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuneralDTO patchFuneral(@PathVariable Long id, @RequestBody FuneralDTO funeralDTO) {
        return funeralService.patchFuneral(id, funeralDTO);
    }

    @DeleteMapping("/funerals/{id}")
    public ResponseEntity deleteFuneral(@PathVariable Long id) {
        return funeralService.deleteFuneralById(id);
    }

}