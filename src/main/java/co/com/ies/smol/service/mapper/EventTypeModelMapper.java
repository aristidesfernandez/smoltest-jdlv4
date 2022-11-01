package co.com.ies.smol.service.mapper;

import co.com.ies.smol.domain.EventType;
import co.com.ies.smol.domain.EventTypeModel;
import co.com.ies.smol.service.dto.EventTypeDTO;
import co.com.ies.smol.service.dto.EventTypeModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventTypeModel} and its DTO {@link EventTypeModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventTypeModelMapper extends EntityMapper<EventTypeModelDTO, EventTypeModel> {
    @Mapping(target = "eventType", source = "eventType", qualifiedByName = "eventTypeId")
    EventTypeModelDTO toDto(EventTypeModel s);

    @Named("eventTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventTypeDTO toDtoEventTypeId(EventType eventType);
}
