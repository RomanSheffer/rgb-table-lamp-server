package rgb.lamp.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.service.PrepareDataForLamp;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(PrepareDataForLamp.class)
class InternetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockitoSpyBean
   private PrepareDataForLamp prepareDataForLamp;
    @MockitoBean
   private LampController lampController;


    @Test
    @DisplayName("счастливый путь, успешный прием данных и проверка ответа")
    @WithMockUser(username = "testUser", roles = "USER")
    void happyPath() throws Exception {

        LampDto lampDto = new LampDto();

        mockMvc.perform(post("/api/lamp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lampDto))
                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("успешно"));

        Mockito.verify(prepareDataForLamp).prepareData(ArgumentMatchers.any(LampDto.class));

    }

}