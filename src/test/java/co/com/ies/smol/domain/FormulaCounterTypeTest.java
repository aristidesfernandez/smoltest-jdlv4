package co.com.ies.smol.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.ies.smol.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormulaCounterTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormulaCounterType.class);
        FormulaCounterType formulaCounterType1 = new FormulaCounterType();
        formulaCounterType1.setId(1L);
        FormulaCounterType formulaCounterType2 = new FormulaCounterType();
        formulaCounterType2.setId(formulaCounterType1.getId());
        assertThat(formulaCounterType1).isEqualTo(formulaCounterType2);
        formulaCounterType2.setId(2L);
        assertThat(formulaCounterType1).isNotEqualTo(formulaCounterType2);
        formulaCounterType1.setId(null);
        assertThat(formulaCounterType1).isNotEqualTo(formulaCounterType2);
    }
}
