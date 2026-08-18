package rgb.lamp.pet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rgb.lamp.pet.dto.LampDto;

import static org.junit.jupiter.api.Assertions.*;


class PrepareDataForLampTest {

    @Test
    @DisplayName("обнулить цвета если люстру выключили командой")
    void ifIsActiveFalse_thenColorsShouldBeNone() {

        //arrange
        PrepareDataForLamp prepareDataForLamp = new PrepareDataForLamp();

        LampDto lampDto = new LampDto();
        lampDto.setRed(300);
        lampDto.setGreen(300);
        lampDto.setBlue(300);
        lampDto.setActive(false);

        //act
        LampDto testingLampDto = prepareDataForLamp.prepareData(lampDto);

        //assert
        assertEquals(0, testingLampDto.getRed());
        assertEquals(0, testingLampDto.getGreen());
        assertEquals(0, testingLampDto.getBlue());
        assertFalse(testingLampDto.isActive());
    }

    @Test
    @DisplayName("если значения цветов больше или меньше крайних точек, установить в крайние точки(0-255)")
    void ifColorsNotInInterval_thenSetItInInterval() {

        //arrange
        PrepareDataForLamp prepareDataForLamp = new PrepareDataForLamp();

        LampDto lampDtoMax = new LampDto();
        lampDtoMax.setRed(300);
        lampDtoMax.setGreen(300);
        lampDtoMax.setBlue(300);
        lampDtoMax.setActive(true);

        LampDto lampDtoMin = new LampDto();
        lampDtoMin.setRed(0);
        lampDtoMin.setGreen(0);
        lampDtoMin.setBlue(0);
        lampDtoMin.setActive(true);

        //act
        LampDto testingLampDtoMax = prepareDataForLamp.prepareData(lampDtoMax);
        LampDto testingLampDtoMin = prepareDataForLamp.prepareData(lampDtoMin);

        //assert
        assertEquals(0, testingLampDtoMin.getRed());
        assertEquals(0, testingLampDtoMin.getGreen());
        assertEquals(0, testingLampDtoMin.getBlue());

        assertEquals(255, testingLampDtoMax.getRed());
        assertEquals(255, testingLampDtoMax.getGreen());
        assertEquals(255, testingLampDtoMax.getBlue());
    }

    @Test
    @DisplayName("тест отрицательных значений")
    void ifColorsAreInMinus_thenSetItInNone() {

        //arrange
        PrepareDataForLamp prepareDataForLamp = new PrepareDataForLamp();

        LampDto lampDto = new LampDto();
        lampDto.setRed(-100);
        lampDto.setGreen(-40);
        lampDto.setBlue(-10);
        lampDto.setActive(true);

        //act
        LampDto testingLampDto = prepareDataForLamp.prepareData(lampDto);

        //assert
        assertEquals(0, testingLampDto.getRed());
        assertEquals(0, testingLampDto.getGreen());
        assertEquals(0, testingLampDto.getBlue());

    }
}