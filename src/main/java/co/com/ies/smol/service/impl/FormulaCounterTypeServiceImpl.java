package co.com.ies.smol.service.impl;

import co.com.ies.smol.domain.FormulaCounterType;
import co.com.ies.smol.repository.FormulaCounterTypeRepository;
import co.com.ies.smol.service.FormulaCounterTypeService;
import co.com.ies.smol.service.dto.FormulaCounterTypeDTO;
import co.com.ies.smol.service.mapper.FormulaCounterTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormulaCounterType}.
 */
@Service
@Transactional
public class FormulaCounterTypeServiceImpl implements FormulaCounterTypeService {

    private final Logger log = LoggerFactory.getLogger(FormulaCounterTypeServiceImpl.class);

    private final FormulaCounterTypeRepository formulaCounterTypeRepository;

    private final FormulaCounterTypeMapper formulaCounterTypeMapper;

    public FormulaCounterTypeServiceImpl(
        FormulaCounterTypeRepository formulaCounterTypeRepository,
        FormulaCounterTypeMapper formulaCounterTypeMapper
    ) {
        this.formulaCounterTypeRepository = formulaCounterTypeRepository;
        this.formulaCounterTypeMapper = formulaCounterTypeMapper;
    }

    @Override
    public FormulaCounterTypeDTO save(FormulaCounterTypeDTO formulaCounterTypeDTO) {
        log.debug("Request to save FormulaCounterType : {}", formulaCounterTypeDTO);
        FormulaCounterType formulaCounterType = formulaCounterTypeMapper.toEntity(formulaCounterTypeDTO);
        formulaCounterType = formulaCounterTypeRepository.save(formulaCounterType);
        return formulaCounterTypeMapper.toDto(formulaCounterType);
    }

    @Override
    public FormulaCounterTypeDTO update(FormulaCounterTypeDTO formulaCounterTypeDTO) {
        log.debug("Request to update FormulaCounterType : {}", formulaCounterTypeDTO);
        FormulaCounterType formulaCounterType = formulaCounterTypeMapper.toEntity(formulaCounterTypeDTO);
        formulaCounterType = formulaCounterTypeRepository.save(formulaCounterType);
        return formulaCounterTypeMapper.toDto(formulaCounterType);
    }

    @Override
    public Optional<FormulaCounterTypeDTO> partialUpdate(FormulaCounterTypeDTO formulaCounterTypeDTO) {
        log.debug("Request to partially update FormulaCounterType : {}", formulaCounterTypeDTO);

        return formulaCounterTypeRepository
            .findById(formulaCounterTypeDTO.getId())
            .map(existingFormulaCounterType -> {
                formulaCounterTypeMapper.partialUpdate(existingFormulaCounterType, formulaCounterTypeDTO);

                return existingFormulaCounterType;
            })
            .map(formulaCounterTypeRepository::save)
            .map(formulaCounterTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormulaCounterTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormulaCounterTypes");
        return formulaCounterTypeRepository.findAll(pageable).map(formulaCounterTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormulaCounterTypeDTO> findOne(Long id) {
        log.debug("Request to get FormulaCounterType : {}", id);
        return formulaCounterTypeRepository.findById(id).map(formulaCounterTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormulaCounterType : {}", id);
        formulaCounterTypeRepository.deleteById(id);
    }
}
