package co.com.ies.smol.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MunicipalityMapperTest {

    private MunicipalityMapper municipalityMapper;

    @BeforeEach
    public void setUp() {
        municipalityMapper = new MunicipalityMapperImpl();
    }
}
