package co.com.ies.smol.service.impl;

import co.com.ies.smol.domain.CommandDevice;
import co.com.ies.smol.repository.CommandDeviceRepository;
import co.com.ies.smol.service.CommandDeviceService;
import co.com.ies.smol.service.dto.CommandDeviceDTO;
import co.com.ies.smol.service.mapper.CommandDeviceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommandDevice}.
 */
@Service
@Transactional
public class CommandDeviceServiceImpl implements CommandDeviceService {

    private final Logger log = LoggerFactory.getLogger(CommandDeviceServiceImpl.class);

    private final CommandDeviceRepository commandDeviceRepository;

    private final CommandDeviceMapper commandDeviceMapper;

    public CommandDeviceServiceImpl(CommandDeviceRepository commandDeviceRepository, CommandDeviceMapper commandDeviceMapper) {
        this.commandDeviceRepository = commandDeviceRepository;
        this.commandDeviceMapper = commandDeviceMapper;
    }

    @Override
    public CommandDeviceDTO save(CommandDeviceDTO commandDeviceDTO) {
        log.debug("Request to save CommandDevice : {}", commandDeviceDTO);
        CommandDevice commandDevice = commandDeviceMapper.toEntity(commandDeviceDTO);
        commandDevice = commandDeviceRepository.save(commandDevice);
        return commandDeviceMapper.toDto(commandDevice);
    }

    @Override
    public CommandDeviceDTO update(CommandDeviceDTO commandDeviceDTO) {
        log.debug("Request to update CommandDevice : {}", commandDeviceDTO);
        CommandDevice commandDevice = commandDeviceMapper.toEntity(commandDeviceDTO);
        commandDevice = commandDeviceRepository.save(commandDevice);
        return commandDeviceMapper.toDto(commandDevice);
    }

    @Override
    public Optional<CommandDeviceDTO> partialUpdate(CommandDeviceDTO commandDeviceDTO) {
        log.debug("Request to partially update CommandDevice : {}", commandDeviceDTO);

        return commandDeviceRepository
            .findById(commandDeviceDTO.getId())
            .map(existingCommandDevice -> {
                commandDeviceMapper.partialUpdate(existingCommandDevice, commandDeviceDTO);

                return existingCommandDevice;
            })
            .map(commandDeviceRepository::save)
            .map(commandDeviceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommandDeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommandDevices");
        return commandDeviceRepository.findAll(pageable).map(commandDeviceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommandDeviceDTO> findOne(Long id) {
        log.debug("Request to get CommandDevice : {}", id);
        return commandDeviceRepository.findById(id).map(commandDeviceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommandDevice : {}", id);
        commandDeviceRepository.deleteById(id);
    }
}
