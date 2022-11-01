package co.com.ies.smol.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.ies.smol.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormulaCounterTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormulaCounterTypeDTO.class);
        FormulaCounterTypeDTO formulaCounterTypeDTO1 = new FormulaCounterTypeDTO();
        formulaCounterTypeDTO1.setId(1L);
        FormulaCounterTypeDTO formulaCounterTypeDTO2 = new FormulaCounterTypeDTO();
        assertThat(formulaCounterTypeDTO1).isNotEqualTo(formulaCounterTypeDTO2);
        formulaCounterTypeDTO2.setId(formulaCounterTypeDTO1.getId());
        assertThat(formulaCounterTypeDTO1).isEqualTo(formulaCounterTypeDTO2);
        formulaCounterTypeDTO2.setId(2L);
        assertThat(formulaCounterTypeDTO1).isNotEqualTo(formulaCounterTypeDTO2);
        formulaCounterTypeDTO1.setId(null);
        assertThat(formulaCounterTypeDTO1).isNotEqualTo(formulaCounterTypeDTO2);
    }
}
