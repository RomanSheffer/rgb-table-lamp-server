package rgb.lamp.pet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LampEntity {

    private int red;
    private int green;
    private int blue;

    private boolean isActive;

}
