package rgb.lamp.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.exceptions.CustomException;
import rgb.lamp.pet.service.LampWebClient;

@Slf4j
@RestController
@RequestMapping("/api/lamp")
@Tag(name = "эндпоинты для работы с умной лампой")
@RequiredArgsConstructor
public class InternetController {

    private final LampWebClient lampWebClient;

    @GetMapping
    public ModelAndView getLampPage() {

        return new ModelAndView("index");

    }

    @PostMapping
    @Operation(summary = "изменить состояние лампы", description = "меняет  текущие цвета RGB и статус горения вкл/выкл")
    public ResponseEntity<String> internetDataForLamp(@Valid @RequestBody LampDto lampDto) {

        try {
            log.info("Принимаем json данные ");

            lampWebClient.sendColor(lampDto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("успешно");

        } catch (Exception e) {

            throw new CustomException("не удалось получить данные от лампы", e, HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/status")
    @Operation(summary = "Получить состояние лампы", description = "Возвращает текущие цвета RGB и статус вкл/выкл ")
    public ResponseEntity<LampDto> checkingStatus(){

        log.info("запрос состояния лампы");

        try {
            LampDto lampResponse = lampWebClient.getStatus();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lampResponse);

        }catch (Exception e){
            log.error("не удалось проверить статус лампы", e);
            throw new CustomException("Ошибка получения состояния лампы", e, HttpStatus.BAD_GATEWAY);
        }

    }

}
