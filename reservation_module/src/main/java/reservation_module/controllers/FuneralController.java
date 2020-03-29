package reservation_module.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
//@RequestMapping(FuneralController.BASE_URL)
public class FuneralController {
//    public static final String BASE_URL = "/api/v1/funerals";

//    private final FuneralService answerService;

    public FuneralController(/*FuneralService funeralService*/) {
//        this.answerService = answerService;
    }

    @GetMapping("/test")
    public String home() {
        return "Hello Test Docker World";
    }

//    @GetMapping("/{id}/answers")
//    @ResponseStatus(HttpStatus.OK)
//    public FuneralListDTO getFuneralsByStudentId(@PathVariable Long id) {
//        return answerService.getAllFuneralsByStudentId(id);
//    }
//
//    @GetMapping("/{studentId}/answers/{answerId}")
//    @ResponseStatus(HttpStatus.OK)
//    public FuneralDTO getFuneralByStudentIdAndFuneralId(@PathVariable Long studentId, @PathVariable Long answerId) {
//        return answerService.getByStudentIdAndFuneralId(studentId, answerId);
//    }
//
//    @PostMapping("/{id}/answers")
//    @ResponseStatus(HttpStatus.CREATED)
//    public FuneralDTO createNewFuneral(@PathVariable Long id, @RequestBody FuneralDTO answerDTO) {
//        return answerService.saveAndReturnDTO(id, answerDTO);
//    }
//
//    @PutMapping("/{id}/answers")
//    @ResponseStatus(HttpStatus.OK)
//    public FuneralDTO updateFuneral(@PathVariable Long id, @RequestBody FuneralDTO answerDTO) {
//        return answerService.saveAndReturnDTO(id, answerDTO);
//    }
//
//    @DeleteMapping("/{studentId}/answers/{answerId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteStudent(@PathVariable Long studentId, @PathVariable Long answerId) {
//        answerService.deleteByStudentIdAndFuneralId(studentId, answerId);
//    }

}