package rgb.lamp.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.exceptions.CustomException;
import rgb.lamp.pet.service.LampWebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(InternetController.class)
class InternetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LampWebClient lampWebClient;

    @Test
    @DisplayName("GET /api/lamp должен возвращать страницу index")
    void getLampPage_shouldReturnModelAndView() throws Exception {
        mockMvc.perform(get("/api/lamp")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("POST /api/lamp должен отправлять цвет и возвращать 200")
    void internetDataForLamp_shouldReturnOk_whenDataIsValid() throws Exception {

        // arrange
        LampDto validDto = new LampDto();
        validDto.setRed(255);
        validDto.setGreen(0);
        validDto.setBlue(0);
        validDto.setActive(true);
        validDto.setWorking(true);

        Mockito.when(lampWebClient.sendColor(any(LampDto.class))).thenReturn(validDto);

        // act assert
        mockMvc.perform(post("/api/lamp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("успешно"));
    }

    @Test
    @DisplayName("GET /api/lamp/status должен возвращать JSON с состоянием лампы")
    void checkingStatus_shouldReturnLampDto() throws Exception {

        // arrange

        LampDto mockResponseDto = new LampDto();
        mockResponseDto.setRed(255);
        mockResponseDto.setGreen(0);
        mockResponseDto.setBlue(0);
        mockResponseDto.setActive(true);
        mockResponseDto.setWorking(true);
        Mockito.when(lampWebClient.getStatus()).thenReturn(mockResponseDto);

        // act assert
        mockMvc.perform(get("/api/lamp/status")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.r").value(255))
                .andExpect(jsonPath("$.g").value(0))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    @DisplayName("GET /api/lamp/status должен швырять BAD_GATEWAY, если упал веб-клиент")
    void checkingStatus_shouldThrowCustomException_whenClientFails() throws Exception {

        // arrange
        Mockito.when(lampWebClient.getStatus())
                .thenThrow(new RuntimeException("Лампа недоступна"));

        // act assert
        mockMvc.perform(get("/api/lamp/status")
                        .with(user("admin").roles("ADMIN")))

                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomException))
                .andExpect(result -> assertEquals("Ошибка получения состояния лампы", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("POST /api/lamp должен возвращать 400 Bad Request, если передан невалидный цвет")
    void internetDataForLamp_shouldReturnBadRequest_whenColorIsInvalid() throws Exception {

        //arrange
        LampDto invalidDto = new LampDto();
        invalidDto.setRed(999);
        invalidDto.setGreen(0);
        invalidDto.setBlue(0);
        invalidDto.setActive(true);
        invalidDto.setWorking(true);

        // act assert
        mockMvc.perform(post("/api/lamp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto))
                        .with(user("ADMIN").roles("ADMIN"))
                        .with(csrf()))

                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(lampWebClient);
    }

}
