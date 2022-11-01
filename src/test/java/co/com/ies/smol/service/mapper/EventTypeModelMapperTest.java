package co.com.ies.smol.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTypeModelMapperTest {

    private EventTypeModelMapper eventTypeModelMapper;

    @BeforeEach
    public void setUp() {
        eventTypeModelMapper = new EventTypeModelMapperImpl();
    }
}
