package co.com.ies.smol.service.mapper;

import co.com.ies.smol.domain.EventDevice;
import co.com.ies.smol.domain.EventType;
import co.com.ies.smol.service.dto.EventDeviceDTO;
import co.com.ies.smol.service.dto.EventTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventDevice} and its DTO {@link EventDeviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventDeviceMapper extends EntityMapper<EventDeviceDTO, EventDevice> {
    @Mapping(target = "eventType", source = "eventType", qualifiedByName = "eventTypeId")
    EventDeviceDTO toDto(EventDevice s);

    @Named("eventTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventTypeDTO toDtoEventTypeId(EventType eventType);
}
