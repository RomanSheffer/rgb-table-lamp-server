package rgb.lamp.pet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.exceptions.CustomException;

@Service
public class LampWebClient {

    private final WebClient lampClient;


    public LampWebClient(WebClient.Builder webClientBuilder,
                         @Value("${lamp_url}") String baseUrl) {
        this.lampClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public LampDto sendColor(LampDto lampDto) {
        return lampClient.post()
                .uri("/api/lamp/color")
                .bodyValue(lampDto)
                .retrieve()
                .bodyToMono(LampDto.class)
                .onErrorMap( e-> new CustomException("ошибка работы lampClient get", e,HttpStatus.BAD_REQUEST) )
                .block();
    }

    public LampDto getStatus() {

            return lampClient.get()
                    .uri("/api/lamp/status")
                    .retrieve()
                    .bodyToMono(LampDto.class)
                    .onErrorMap( e-> new CustomException("ошибка работы lampClient get", e,HttpStatus.BAD_REQUEST) )

                    .block();


    }



}



