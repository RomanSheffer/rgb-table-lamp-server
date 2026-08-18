package rgb.lamp.pet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.service.PrepareDataForLamp;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/lamp")
@RequiredArgsConstructor
public class InternetController {

    private final LampController lampController;
    private final PrepareDataForLamp prepareDataForLamp;

    @GetMapping
    public ModelAndView getLampPage() {

        return new ModelAndView("index");

    }

    @PostMapping
    public ResponseEntity<String> internetDataForLamp(@RequestBody LampDto lampDto) {

        try {
            log.info("Принимаем json данные ");
            LampDto validDataOfLamp = prepareDataForLamp.prepareData(lampDto);

            lampController.sendCommand(validDataOfLamp);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("успешно");
        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("некорректный json");
        }

    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkingStatus(){

        log.info("запрос состояния лампы");

        Map<String, Object> response = new HashMap<>();
        try {
            ResponseEntity<String> lampResponse = lampController.checkStatus();

            if (lampResponse.getStatusCode().is2xxSuccessful()) {
                response.put("isActive", true);
            } else {
                response.put("isActive", false);
            }
        }catch (Exception e){
            log.error("не удалось проверить статус лампы", e);
            response.put("isActive", false);
        }
        return ResponseEntity.ok(response);

    }

}
