package co.com.ies.smol.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.ies.smol.domain.CommandModel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandModelDTO implements Serializable {

    private Long id;

    @Size(max = 2)
    private String codeSAS;

    private CommandDTO command;

    private ModelDTO model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSAS() {
        return codeSAS;
    }

    public void setCodeSAS(String codeSAS) {
        this.codeSAS = codeSAS;
    }

    public CommandDTO getCommand() {
        return command;
    }

    public void setCommand(CommandDTO command) {
        this.command = command;
    }

    public ModelDTO getModel() {
        return model;
    }

    public void setModel(ModelDTO model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandModelDTO)) {
            return false;
        }

        CommandModelDTO commandModelDTO = (CommandModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandModelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandModelDTO{" +
            "id=" + getId() +
            ", codeSAS='" + getCodeSAS() + "'" +
            ", command=" + getCommand() +
            ", model=" + getModel() +
            "}";
    }
}
