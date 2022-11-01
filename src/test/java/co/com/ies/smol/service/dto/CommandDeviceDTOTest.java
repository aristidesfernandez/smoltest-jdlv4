package co.com.ies.smol.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.ies.smol.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandDeviceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandDeviceDTO.class);
        CommandDeviceDTO commandDeviceDTO1 = new CommandDeviceDTO();
        commandDeviceDTO1.setId(1L);
        CommandDeviceDTO commandDeviceDTO2 = new CommandDeviceDTO();
        assertThat(commandDeviceDTO1).isNotEqualTo(commandDeviceDTO2);
        commandDeviceDTO2.setId(commandDeviceDTO1.getId());
        assertThat(commandDeviceDTO1).isEqualTo(commandDeviceDTO2);
        commandDeviceDTO2.setId(2L);
        assertThat(commandDeviceDTO1).isNotEqualTo(commandDeviceDTO2);
        commandDeviceDTO1.setId(null);
        assertThat(commandDeviceDTO1).isNotEqualTo(commandDeviceDTO2);
    }
}
