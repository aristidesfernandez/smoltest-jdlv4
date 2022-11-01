package co.com.ies.smol.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.ies.smol.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventTypeModelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventTypeModelDTO.class);
        EventTypeModelDTO eventTypeModelDTO1 = new EventTypeModelDTO();
        eventTypeModelDTO1.setId(1L);
        EventTypeModelDTO eventTypeModelDTO2 = new EventTypeModelDTO();
        assertThat(eventTypeModelDTO1).isNotEqualTo(eventTypeModelDTO2);
        eventTypeModelDTO2.setId(eventTypeModelDTO1.getId());
        assertThat(eventTypeModelDTO1).isEqualTo(eventTypeModelDTO2);
        eventTypeModelDTO2.setId(2L);
        assertThat(eventTypeModelDTO1).isNotEqualTo(eventTypeModelDTO2);
        eventTypeModelDTO1.setId(null);
        assertThat(eventTypeModelDTO1).isNotEqualTo(eventTypeModelDTO2);
    }
}
