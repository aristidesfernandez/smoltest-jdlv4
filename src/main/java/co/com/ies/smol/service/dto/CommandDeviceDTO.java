package co.com.ies.smol.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.ies.smol.domain.CommandDevice} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandDeviceDTO implements Serializable {

    private Long id;

    private CommandDTO command;

    private DeviceDTO device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommandDTO getCommand() {
        return command;
    }

    public void setCommand(CommandDTO command) {
        this.command = command;
    }

    public DeviceDTO getDevice() {
        return device;
    }

    public void setDevice(DeviceDTO device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandDeviceDTO)) {
            return false;
        }

        CommandDeviceDTO commandDeviceDTO = (CommandDeviceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandDeviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandDeviceDTO{" +
            "id=" + getId() +
            ", command=" + getCommand() +
            ", device=" + getDevice() +
            "}";
    }
}
