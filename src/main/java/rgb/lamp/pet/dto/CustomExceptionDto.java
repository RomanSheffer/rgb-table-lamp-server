package rgb.lamp.pet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class CustomExceptionDto {

    private Instant dateTime;
    private String status;
    private String message;

}
