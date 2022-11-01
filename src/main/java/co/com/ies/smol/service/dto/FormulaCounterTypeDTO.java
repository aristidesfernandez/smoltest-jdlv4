package co.com.ies.smol.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.com.ies.smol.domain.FormulaCounterType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormulaCounterTypeDTO implements Serializable {

    private Long id;

    private FormulaDTO formula;

    private CounterTypeDTO counterType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormulaDTO getFormula() {
        return formula;
    }

    public void setFormula(FormulaDTO formula) {
        this.formula = formula;
    }

    public CounterTypeDTO getCounterType() {
        return counterType;
    }

    public void setCounterType(CounterTypeDTO counterType) {
        this.counterType = counterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormulaCounterTypeDTO)) {
            return false;
        }

        FormulaCounterTypeDTO formulaCounterTypeDTO = (FormulaCounterTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formulaCounterTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormulaCounterTypeDTO{" +
            "id=" + getId() +
            ", formula=" + getFormula() +
            ", counterType=" + getCounterType() +
            "}";
    }
}
