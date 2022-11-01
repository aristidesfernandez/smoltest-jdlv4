package co.com.ies.smol.service.mapper;

import co.com.ies.smol.domain.Establishment;
import co.com.ies.smol.domain.Isle;
import co.com.ies.smol.service.dto.EstablishmentDTO;
import co.com.ies.smol.service.dto.IsleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Isle} and its DTO {@link IsleDTO}.
 */
@Mapper(componentModel = "spring")
public interface IsleMapper extends EntityMapper<IsleDTO, Isle> {
    @Mapping(target = "establishment", source = "establishment", qualifiedByName = "establishmentId")
    IsleDTO toDto(Isle s);

    @Named("establishmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstablishmentDTO toDtoEstablishmentId(Establishment establishment);
}
