package co.com.ies.smol.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.ies.smol.domain.Command} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String code;

    @Size(max = 100)
    private String description;

    @Size(max = 100)
    private String processor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandDTO)) {
            return false;
        }

        CommandDTO commandDTO = (CommandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", processor='" + getProcessor() + "'" +
            "}";
    }
}
