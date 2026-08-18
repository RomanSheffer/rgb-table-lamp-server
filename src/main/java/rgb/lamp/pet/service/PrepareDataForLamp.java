package rgb.lamp.pet.service;

import org.springframework.stereotype.Service;
import rgb.lamp.pet.dto.LampDto;

@Service
public class PrepareDataForLamp {

    public LampDto prepareData(LampDto lampDto) {

        if (!lampDto.isActive()){
            lampDto.setRed(0);
            lampDto.setGreen(0);
            lampDto.setBlue(0);

        } else {
            lampDto.setRed(validationColorOfLamp(lampDto.getRed()));
            lampDto.setGreen(validationColorOfLamp(lampDto.getGreen()));
            lampDto.setBlue(validationColorOfLamp(lampDto.getBlue()));
        }
        return lampDto;
    }

    private int validationColorOfLamp(int data) {

        int validatedData = data;

        if (data > 100) {
            validatedData = 255;
        }
        if (data < 0) {
            validatedData = 0;
        }
        return validatedData;

    }
}
