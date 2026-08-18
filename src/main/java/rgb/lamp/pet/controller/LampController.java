package rgb.lamp.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import rgb.lamp.pet.dto.LampDto;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/color")
public class LampController {

    private final RestClient restClient = RestClient.create();
    private final RestTemplate restTemplate = new RestTemplate();
    private final String lampUrl = "http://192.168.1.200/api/color";

    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping
    public ResponseEntity<LampDto> sendCommand(LampDto lampDto) {
        log.info("отправка данных на контроллер лампы");

        try {

            String jsonBody = objectMapper.writeValueAsString(lampDto);

            restClient.post()
                    .uri(lampUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonBody)
                    .retrieve()
                    .toBodilessEntity();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lampDto);

        } catch (Exception e) {
            log.error("Ошибка отправки: ", e);

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE.value())
                    .body(null);
        }

    }

    //status чтобы избежать конфликта post запросов в java,  у esp один эндпоинт
    @PostMapping("/status")
    public ResponseEntity<String> checkStatus(){

        Map<String, Integer> requestForJson = new HashMap<>();
        requestForJson.put("isAviable", 1);
        log.info("устанавливаем связь с лампой");
        try {

            String jsonBody = objectMapper.writeValueAsString(requestForJson);

            restClient.post()
                    .uri(lampUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonBody)
                    .retrieve()
                    .toBodilessEntity();

            return  ResponseEntity.ok("успешно, лампа доступна");

        } catch (Exception e) {
            log.error("ошибка связи с лампой, лампа недоступна", e);

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE.value())
                    .body("Лампа не доступна");
        }

    }
}
