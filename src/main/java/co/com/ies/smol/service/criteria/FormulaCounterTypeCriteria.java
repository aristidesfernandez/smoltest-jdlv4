package co.com.ies.smol.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link co.com.ies.smol.domain.FormulaCounterType} entity. This class is used
 * in {@link co.com.ies.smol.web.rest.FormulaCounterTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /formula-counter-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormulaCounterTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter formulaId;

    private StringFilter counterTypeId;

    private Boolean distinct;

    public FormulaCounterTypeCriteria() {}

    public FormulaCounterTypeCriteria(FormulaCounterTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.formulaId = other.formulaId == null ? null : other.formulaId.copy();
        this.counterTypeId = other.counterTypeId == null ? null : other.counterTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FormulaCounterTypeCriteria copy() {
        return new FormulaCounterTypeCriteria(this);
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

    public LongFilter getFormulaId() {
        return formulaId;
    }

    public LongFilter formulaId() {
        if (formulaId == null) {
            formulaId = new LongFilter();
        }
        return formulaId;
    }

    public void setFormulaId(LongFilter formulaId) {
        this.formulaId = formulaId;
    }

    public StringFilter getCounterTypeId() {
        return counterTypeId;
    }

    public StringFilter counterTypeId() {
        if (counterTypeId == null) {
            counterTypeId = new StringFilter();
        }
        return counterTypeId;
    }

    public void setCounterTypeId(StringFilter counterTypeId) {
        this.counterTypeId = counterTypeId;
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
        final FormulaCounterTypeCriteria that = (FormulaCounterTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(formulaId, that.formulaId) &&
            Objects.equals(counterTypeId, that.counterTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, formulaId, counterTypeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormulaCounterTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (formulaId != null ? "formulaId=" + formulaId + ", " : "") +
            (counterTypeId != null ? "counterTypeId=" + counterTypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
