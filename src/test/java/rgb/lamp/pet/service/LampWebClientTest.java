package rgb.lamp.pet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.exceptions.CustomException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LampWebClientTest {


    private MockWebServer mockWebServer;
    private LampWebClient lampWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient.Builder testBuilder = WebClient.builder();

        String mockServerUrl = mockWebServer.url("/").toString();

        lampWebClient = new LampWebClient(testBuilder, mockServerUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Проверка корректности отправки данных через LampWebClient ")
    void getStatus_shouldReturnLampDto_whenResponseIsSuccessful() throws Exception {

        //arrange
        LampDto expectedDto = new LampDto();

        expectedDto.setRed(200);
        expectedDto.setGreen(10);
        expectedDto.setBlue(210);

        expectedDto.setActive(true);
        expectedDto.setWorking(true);

        String jsonResponse = objectMapper.writeValueAsString(expectedDto);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(jsonResponse));

        // act
        LampDto resultDto = lampWebClient.getStatus();

        // asserts
        assertNotNull(resultDto);

        assertEquals(200, resultDto.getRed());
        assertEquals(10, resultDto.getGreen());
        assertEquals(210, resultDto.getBlue());

        assertTrue(resultDto.isActive());
        assertTrue(resultDto.isWorking());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/api/lamp/status", recordedRequest.getPath());
    }

    @Test
    @DisplayName("Проверка успешной отправки цвета на лампу через POST")
    void sendColor_shouldPostCorrectBodyAndReturnDto_whenResponseIsSuccessful() throws Exception {

        // Arrange
        LampDto colorToSend  = new LampDto();

        colorToSend.setRed(255);
        colorToSend.setGreen(128);
        colorToSend.setBlue(0);

        colorToSend.setActive(true);
        colorToSend.setWorking(true);

        String mockJsonResponse = objectMapper.writeValueAsString(colorToSend);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mockJsonResponse));

        // Act
        LampDto resultDto = lampWebClient.sendColor(colorToSend);

        // asserts
        assertNotNull(resultDto);
        assertEquals(255, resultDto.getRed());
        assertEquals(128, resultDto.getGreen());
        assertTrue(resultDto.isWorking());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/api/lamp/color", recordedRequest.getPath());

    }


    @Test
    @DisplayName("getStatus должен выбрасывать CustomException, если сервер вернул 500 Internal Server Error")
    void getStatus_shouldThrowCustomException_whenServerReturns500() {
        // arrange
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        // act assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            lampWebClient.getStatus();
        });

        assertEquals("ошибка работы lampClient get", exception.getMessage());
    }

    @Test
    @DisplayName("sendColor должен выбрасывать CustomException, если сервер вернул 404 Not Found")
    void sendColor_shouldThrowCustomException_whenServerReturns404() {

        // arrange:
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404));

        LampDto testDto = new LampDto();
        testDto.setRed(255);
        testDto.setGreen(128);
        testDto.setBlue(0);

        testDto.setActive(true);
        testDto.setWorking(true);

        // act assert:
        CustomException exception = assertThrows(CustomException.class, () -> {
            lampWebClient.sendColor(testDto);
        });

        assertEquals("ошибка работы lampClient get", exception.getMessage());
    }



}

