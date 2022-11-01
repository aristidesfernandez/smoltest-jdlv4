package co.com.ies.smol.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.ies.smol.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandDeviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandDevice.class);
        CommandDevice commandDevice1 = new CommandDevice();
        commandDevice1.setId(1L);
        CommandDevice commandDevice2 = new CommandDevice();
        commandDevice2.setId(commandDevice1.getId());
        assertThat(commandDevice1).isEqualTo(commandDevice2);
        commandDevice2.setId(2L);
        assertThat(commandDevice1).isNotEqualTo(commandDevice2);
        commandDevice1.setId(null);
        assertThat(commandDevice1).isNotEqualTo(commandDevice2);
    }
}
