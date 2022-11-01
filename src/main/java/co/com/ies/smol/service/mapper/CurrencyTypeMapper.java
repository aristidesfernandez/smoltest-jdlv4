package co.com.ies.smol.service.mapper;

import co.com.ies.smol.domain.CurrencyType;
import co.com.ies.smol.domain.Establishment;
import co.com.ies.smol.service.dto.CurrencyTypeDTO;
import co.com.ies.smol.service.dto.EstablishmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurrencyType} and its DTO {@link CurrencyTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CurrencyTypeMapper extends EntityMapper<CurrencyTypeDTO, CurrencyType> {
    @Mapping(target = "establishment", source = "establishment", qualifiedByName = "establishmentId")
    CurrencyTypeDTO toDto(CurrencyType s);

    @Named("establishmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EstablishmentDTO toDtoEstablishmentId(Establishment establishment);
}
