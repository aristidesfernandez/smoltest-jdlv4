package co.com.ies.smol.service.impl;

import co.com.ies.smol.domain.EventTypeModel;
import co.com.ies.smol.repository.EventTypeModelRepository;
import co.com.ies.smol.service.EventTypeModelService;
import co.com.ies.smol.service.dto.EventTypeModelDTO;
import co.com.ies.smol.service.mapper.EventTypeModelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventTypeModel}.
 */
@Service
@Transactional
public class EventTypeModelServiceImpl implements EventTypeModelService {

    private final Logger log = LoggerFactory.getLogger(EventTypeModelServiceImpl.class);

    private final EventTypeModelRepository eventTypeModelRepository;

    private final EventTypeModelMapper eventTypeModelMapper;

    public EventTypeModelServiceImpl(EventTypeModelRepository eventTypeModelRepository, EventTypeModelMapper eventTypeModelMapper) {
        this.eventTypeModelRepository = eventTypeModelRepository;
        this.eventTypeModelMapper = eventTypeModelMapper;
    }

    @Override
    public EventTypeModelDTO save(EventTypeModelDTO eventTypeModelDTO) {
        log.debug("Request to save EventTypeModel : {}", eventTypeModelDTO);
        EventTypeModel eventTypeModel = eventTypeModelMapper.toEntity(eventTypeModelDTO);
        eventTypeModel = eventTypeModelRepository.save(eventTypeModel);
        return eventTypeModelMapper.toDto(eventTypeModel);
    }

    @Override
    public EventTypeModelDTO update(EventTypeModelDTO eventTypeModelDTO) {
        log.debug("Request to update EventTypeModel : {}", eventTypeModelDTO);
        EventTypeModel eventTypeModel = eventTypeModelMapper.toEntity(eventTypeModelDTO);
        eventTypeModel = eventTypeModelRepository.save(eventTypeModel);
        return eventTypeModelMapper.toDto(eventTypeModel);
    }

    @Override
    public Optional<EventTypeModelDTO> partialUpdate(EventTypeModelDTO eventTypeModelDTO) {
        log.debug("Request to partially update EventTypeModel : {}", eventTypeModelDTO);

        return eventTypeModelRepository
            .findById(eventTypeModelDTO.getId())
            .map(existingEventTypeModel -> {
                eventTypeModelMapper.partialUpdate(existingEventTypeModel, eventTypeModelDTO);

                return existingEventTypeModel;
            })
            .map(eventTypeModelRepository::save)
            .map(eventTypeModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventTypeModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventTypeModels");
        return eventTypeModelRepository.findAll(pageable).map(eventTypeModelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventTypeModelDTO> findOne(Long id) {
        log.debug("Request to get EventTypeModel : {}", id);
        return eventTypeModelRepository.findById(id).map(eventTypeModelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventTypeModel : {}", id);
        eventTypeModelRepository.deleteById(id);
    }
}
