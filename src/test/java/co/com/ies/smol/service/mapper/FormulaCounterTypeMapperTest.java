package co.com.ies.smol.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormulaCounterTypeMapperTest {

    private FormulaCounterTypeMapper formulaCounterTypeMapper;

    @BeforeEach
    public void setUp() {
        formulaCounterTypeMapper = new FormulaCounterTypeMapperImpl();
    }
}
