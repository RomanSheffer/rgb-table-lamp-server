package rgb.lamp.pet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LampDto {


    @JsonProperty("r")
    @Range(min = 0, max = 255, message = "Цвет должен быть в диапазоне от 0 до 255")
    @Schema(name = "r", description = "Интенсивность красного цвета", example = "255")
    private int red;

    @JsonProperty("g")
    @Range(min = 0, max = 255, message = "Цвет должен быть в диапазоне от 0 до 255")
    @Schema(name = "g", description = "Интенсивность зелёного цвета", example = "120")
    private int green;

    @JsonProperty("b")
    @Range(min = 0, max = 255, message = "Цвет должен быть в диапазоне от 0 до 255")
    @Schema(name = "b", description = "Интенсивность синего цвета", example = "0")
    private int blue;

    @JsonProperty("isActive")
    @NotNull(message = "Статус доступности лампы в сети обязателен")
    @Schema(name = "isActive", description = "Флаг доступности лампы в  сети", example = "true")
    private boolean isActive;

    @JsonProperty("isWorking")
    @NotNull(message = "Статус активности обязателен")
    @Schema(name = "isWorking", description = "Флаг состояния лампы (вкл/выкл)", example = "false")
    private boolean isWorking;

    }