package co.com.ies.smol.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link co.com.ies.smol.domain.EventTypeModel} entity. This class is used
 * in {@link co.com.ies.smol.web.rest.EventTypeModelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-type-models?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventTypeModelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter modelId;

    private LongFilter eventTypeId;

    private Boolean distinct;

    public EventTypeModelCriteria() {}

    public EventTypeModelCriteria(EventTypeModelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.modelId = other.modelId == null ? null : other.modelId.copy();
        this.eventTypeId = other.eventTypeId == null ? null : other.eventTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventTypeModelCriteria copy() {
        return new EventTypeModelCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getModelId() {
        return modelId;
    }

    public IntegerFilter modelId() {
        if (modelId == null) {
            modelId = new IntegerFilter();
        }
        return modelId;
    }

    public void setModelId(IntegerFilter modelId) {
        this.modelId = modelId;
    }

    public LongFilter getEventTypeId() {
        return eventTypeId;
    }

    public LongFilter eventTypeId() {
        if (eventTypeId == null) {
            eventTypeId = new LongFilter();
        }
        return eventTypeId;
    }

    public void setEventTypeId(LongFilter eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventTypeModelCriteria that = (EventTypeModelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(modelId, that.modelId) &&
            Objects.equals(eventTypeId, that.eventTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelId, eventTypeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventTypeModelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (modelId != null ? "modelId=" + modelId + ", " : "") +
            (eventTypeId != null ? "eventTypeId=" + eventTypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
