package co.com.ies.smol.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.com.ies.smol.domain.CurrencyType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CurrencyTypeDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 3)
    private String code;

    @NotNull
    @Size(max = 5)
    private String symbol;

    private Boolean isPriority;

    @Size(max = 50)
    private String location;

    private Float exchangeRate;

    private Integer decimalPlaces;

    @Size(max = 1)
    private String decimalSeparator;

    @Size(max = 1)
    private String thousandSeparator;

    @Size(max = 100)
    private String description;

    private EstablishmentDTO establishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Boolean getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(Boolean isPriority) {
        this.isPriority = isPriority;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public String getThousandSeparator() {
        return thousandSeparator;
    }

    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EstablishmentDTO getEstablishment() {
        return establishment;
    }

    public void setEstablishment(EstablishmentDTO establishment) {
        this.establishment = establishment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyTypeDTO)) {
            return false;
        }

        CurrencyTypeDTO currencyTypeDTO = (CurrencyTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, currencyTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyTypeDTO{" +
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
            ", establishment=" + getEstablishment() +
            "}";
    }
}
