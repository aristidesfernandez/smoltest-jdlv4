package co.com.ies.smol.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormulaCounterType.
 */
@Entity
@Table(name = "formula_counter_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormulaCounterType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Formula formula;

    @ManyToOne
    @JsonIgnoreProperties(value = { "formulaCounterTypes" }, allowSetters = true)
    private CounterType counterType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormulaCounterType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formula getFormula() {
        return this.formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public FormulaCounterType formula(Formula formula) {
        this.setFormula(formula);
        return this;
    }

    public CounterType getCounterType() {
        return this.counterType;
    }

    public void setCounterType(CounterType counterType) {
        this.counterType = counterType;
    }

    public FormulaCounterType counterType(CounterType counterType) {
        this.setCounterType(counterType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormulaCounterType)) {
            return false;
        }
        return id != null && id.equals(((FormulaCounterType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormulaCounterType{" +
            "id=" + getId() +
            "}";
    }
}
