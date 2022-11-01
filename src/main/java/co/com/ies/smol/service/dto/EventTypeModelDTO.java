package co.com.ies.smol.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.com.ies.smol.domain.EventTypeModel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventTypeModelDTO implements Serializable {

    private Long id;

    private Integer modelId;

    private EventTypeDTO eventType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public EventTypeDTO getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeDTO eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventTypeModelDTO)) {
            return false;
        }

        EventTypeModelDTO eventTypeModelDTO = (EventTypeModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventTypeModelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventTypeModelDTO{" +
            "id=" + getId() +
            ", modelId=" + getModelId() +
            ", eventType=" + getEventType() +
            "}";
    }
}
