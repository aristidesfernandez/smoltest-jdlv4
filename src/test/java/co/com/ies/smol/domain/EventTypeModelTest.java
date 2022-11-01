package co.com.ies.smol.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.ies.smol.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventTypeModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventTypeModel.class);
        EventTypeModel eventTypeModel1 = new EventTypeModel();
        eventTypeModel1.setId(1L);
        EventTypeModel eventTypeModel2 = new EventTypeModel();
        eventTypeModel2.setId(eventTypeModel1.getId());
        assertThat(eventTypeModel1).isEqualTo(eventTypeModel2);
        eventTypeModel2.setId(2L);
        assertThat(eventTypeModel1).isNotEqualTo(eventTypeModel2);
        eventTypeModel1.setId(null);
        assertThat(eventTypeModel1).isNotEqualTo(eventTypeModel2);
    }
}
