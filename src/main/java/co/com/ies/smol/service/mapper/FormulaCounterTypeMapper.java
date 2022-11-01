package co.com.ies.smol.service.mapper;

import co.com.ies.smol.domain.CounterType;
import co.com.ies.smol.domain.Formula;
import co.com.ies.smol.domain.FormulaCounterType;
import co.com.ies.smol.service.dto.CounterTypeDTO;
import co.com.ies.smol.service.dto.FormulaCounterTypeDTO;
import co.com.ies.smol.service.dto.FormulaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormulaCounterType} and its DTO {@link FormulaCounterTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FormulaCounterTypeMapper extends EntityMapper<FormulaCounterTypeDTO, FormulaCounterType> {
    @Mapping(target = "formula", source = "formula", qualifiedByName = "formulaId")
    @Mapping(target = "counterType", source = "counterType", qualifiedByName = "counterTypeCounterCode")
    FormulaCounterTypeDTO toDto(FormulaCounterType s);

    @Named("formulaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FormulaDTO toDtoFormulaId(Formula formula);

    @Named("counterTypeCounterCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "counterCode", source = "counterCode")
    CounterTypeDTO toDtoCounterTypeCounterCode(CounterType counterType);
}
