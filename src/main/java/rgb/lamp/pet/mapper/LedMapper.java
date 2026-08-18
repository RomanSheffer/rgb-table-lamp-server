package rgb.lamp.pet.mapper;


import ch.qos.logback.core.model.ComponentModel;
import org.mapstruct.Mapper;
import rgb.lamp.pet.dto.LampDto;
import rgb.lamp.pet.model.LampEntity;

@Mapper(componentModel = "spring")
public interface LedMapper {

    LampDto LampToDto(LampEntity lampEntity);
    LampEntity LampDtoToEntity(LampDto lampDto);

}
