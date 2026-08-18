package rgb.lamp.pet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LampDto {

    @JsonProperty("r")
    private int red;

    @JsonProperty("g")
    private int green;

    @JsonProperty("b")
    private int blue;

    @JsonProperty("isActive")
    private boolean isActive;


}
