package co.com.ies.smol.service.mapper;

import co.com.ies.smol.domain.Command;
import co.com.ies.smol.domain.CommandDevice;
import co.com.ies.smol.domain.Device;
import co.com.ies.smol.service.dto.CommandDTO;
import co.com.ies.smol.service.dto.CommandDeviceDTO;
import co.com.ies.smol.service.dto.DeviceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandDevice} and its DTO {@link CommandDeviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandDeviceMapper extends EntityMapper<CommandDeviceDTO, CommandDevice> {
    @Mapping(target = "command", source = "command", qualifiedByName = "commandId")
    @Mapping(target = "device", source = "device", qualifiedByName = "deviceId")
    CommandDeviceDTO toDto(CommandDevice s);

    @Named("commandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommandDTO toDtoCommandId(Command command);

    @Named("deviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeviceDTO toDtoDeviceId(Device device);
}
