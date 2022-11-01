package co.com.ies.smol.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link co.com.ies.smol.domain.CurrencyType} entity. This class is used
 * in {@link co.com.ies.smol.web.rest.CurrencyTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /currency-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CurrencyTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter symbol;

    private BooleanFilter isPriority;

    private StringFilter location;

    private FloatFilter exchangeRate;

    private IntegerFilter decimalPlaces;

    private StringFilter decimalSeparator;

    private StringFilter thousandSeparator;

    private StringFilter description;

    private LongFilter establishmentId;

    private Boolean distinct;

    public CurrencyTypeCriteria() {}

    public CurrencyTypeCriteria(CurrencyTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.symbol = other.symbol == null ? null : other.symbol.copy();
        this.isPriority = other.isPriority == null ? null : other.isPriority.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.exchangeRate = other.exchangeRate == null ? null : other.exchangeRate.copy();
        this.decimalPlaces = other.decimalPlaces == null ? null : other.decimalPlaces.copy();
        this.decimalSeparator = other.decimalSeparator == null ? null : other.decimalSeparator.copy();
        this.thousandSeparator = other.thousandSeparator == null ? null : other.thousandSeparator.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.establishmentId = other.establishmentId == null ? null : other.establishmentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CurrencyTypeCriteria copy() {
        return new CurrencyTypeCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getSymbol() {
        return symbol;
    }

    public StringFilter symbol() {
        if (symbol == null) {
            symbol = new StringFilter();
        }
        return symbol;
    }

    public void setSymbol(StringFilter symbol) {
        this.symbol = symbol;
    }

    public BooleanFilter getIsPriority() {
        return isPriority;
    }

    public BooleanFilter isPriority() {
        if (isPriority == null) {
            isPriority = new BooleanFilter();
        }
        return isPriority;
    }

    public void setIsPriority(BooleanFilter isPriority) {
        this.isPriority = isPriority;
    }

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public FloatFilter getExchangeRate() {
        return exchangeRate;
    }

    public FloatFilter exchangeRate() {
        if (exchangeRate == null) {
            exchangeRate = new FloatFilter();
        }
        return exchangeRate;
    }

    public void setExchangeRate(FloatFilter exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public IntegerFilter getDecimalPlaces() {
        return decimalPlaces;
    }

    public IntegerFilter decimalPlaces() {
        if (decimalPlaces == null) {
            decimalPlaces = new IntegerFilter();
        }
        return decimalPlaces;
    }

    public void setDecimalPlaces(IntegerFilter decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public StringFilter getDecimalSeparator() {
        return decimalSeparator;
    }

    public StringFilter decimalSeparator() {
        if (decimalSeparator == null) {
            decimalSeparator = new StringFilter();
        }
        return decimalSeparator;
    }

    public void setDecimalSeparator(StringFilter decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public StringFilter getThousandSeparator() {
        return thousandSeparator;
    }

    public StringFilter thousandSeparator() {
        if (thousandSeparator == null) {
            thousandSeparator = new StringFilter();
        }
        return thousandSeparator;
    }

    public void setThousandSeparator(StringFilter thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getEstablishmentId() {
        return establishmentId;
    }

    public LongFilter establishmentId() {
        if (establishmentId == null) {
            establishmentId = new LongFilter();
        }
        return establishmentId;
    }

    public void setEstablishmentId(LongFilter establishmentId) {
        this.establishmentId = establishmentId;
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
        final CurrencyTypeCriteria that = (CurrencyTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(isPriority, that.isPriority) &&
            Objects.equals(location, that.location) &&
            Objects.equals(exchangeRate, that.exchangeRate) &&
            Objects.equals(decimalPlaces, that.decimalPlaces) &&
            Objects.equals(decimalSeparator, that.decimalSeparator) &&
            Objects.equals(thousandSeparator, that.thousandSeparator) &&
            Objects.equals(description, that.description) &&
            Objects.equals(establishmentId, that.establishmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            code,
            symbol,
            isPriority,
            location,
            exchangeRate,
            decimalPlaces,
            decimalSeparator,
            thousandSeparator,
            description,
            establishmentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (symbol != null ? "symbol=" + symbol + ", " : "") +
            (isPriority != null ? "isPriority=" + isPriority + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (exchangeRate != null ? "exchangeRate=" + exchangeRate + ", " : "") +
            (decimalPlaces != null ? "decimalPlaces=" + decimalPlaces + ", " : "") +
            (decimalSeparator != null ? "decimalSeparator=" + decimalSeparator + ", " : "") +
            (thousandSeparator != null ? "thousandSeparator=" + thousandSeparator + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (establishmentId != null ? "establishmentId=" + establishmentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
