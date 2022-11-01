package co.com.ies.smol.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CurrencyType.
 */
@Entity
@Table(name = "currency_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CurrencyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Size(max = 3)
    @Column(name = "code", length = 3, nullable = false)
    private String code;

    @NotNull
    @Size(max = 5)
    @Column(name = "symbol", length = 5, nullable = false)
    private String symbol;

    @Column(name = "is_priority")
    private Boolean isPriority;

    @Size(max = 50)
    @Column(name = "location", length = 50)
    private String location;

    @Column(name = "exchange_rate")
    private Float exchangeRate;

    @Column(name = "decimal_places")
    private Integer decimalPlaces;

    @Size(max = 1)
    @Column(name = "decimal_separator", length = 1)
    private String decimalSeparator;

    @Size(max = 1)
    @Column(name = "thousand_separator", length = 1)
    private String thousandSeparator;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "operator", "municipality" }, allowSetters = true)
    private Establishment establishment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CurrencyType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CurrencyType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public CurrencyType code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public CurrencyType symbol(String symbol) {
        this.setSymbol(symbol);
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Boolean getIsPriority() {
        return this.isPriority;
    }

    public CurrencyType isPriority(Boolean isPriority) {
        this.setIsPriority(isPriority);
        return this;
    }

    public void setIsPriority(Boolean isPriority) {
        this.isPriority = isPriority;
    }

    public String getLocation() {
        return this.location;
    }

    public CurrencyType location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getExchangeRate() {
        return this.exchangeRate;
    }

    public CurrencyType exchangeRate(Float exchangeRate) {
        this.setExchangeRate(exchangeRate);
        return this;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getDecimalPlaces() {
        return this.decimalPlaces;
    }

    public CurrencyType decimalPlaces(Integer decimalPlaces) {
        this.setDecimalPlaces(decimalPlaces);
        return this;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getDecimalSeparator() {
        return this.decimalSeparator;
    }

    public CurrencyType decimalSeparator(String decimalSeparator) {
        this.setDecimalSeparator(decimalSeparator);
        return this;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public String getThousandSeparator() {
        return this.thousandSeparator;
    }

    public CurrencyType thousandSeparator(String thousandSeparator) {
        this.setThousandSeparator(thousandSeparator);
        return this;
    }

    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }

    public String getDescription() {
        return this.description;
    }

    public CurrencyType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Establishment getEstablishment() {
        return this.establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public CurrencyType establishment(Establishment establishment) {
        this.setEstablishment(establishment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyType)) {
            return false;
        }
        return id != null && id.equals(((CurrencyType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", isPriority='" + getIsPriority() + "'" +
            ", location='" + getLocation() + "'" +
            ", exchangeRate=" + getExchangeRate() +
            ", decimalPlaces=" + getDecimalPlaces() +
            ", decimalSeparator='" + getDecimalSeparator() + "'" +
            ", thousandSeparator='" + getThousandSeparator() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
