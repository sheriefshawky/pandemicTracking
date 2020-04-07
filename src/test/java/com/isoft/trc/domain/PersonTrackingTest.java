package com.isoft.trc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.trc.web.rest.TestUtil;

public class PersonTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonTracking.class);
        PersonTracking personTracking1 = new PersonTracking();
        personTracking1.setId(1L);
        PersonTracking personTracking2 = new PersonTracking();
        personTracking2.setId(personTracking1.getId());
        assertThat(personTracking1).isEqualTo(personTracking2);
        personTracking2.setId(2L);
        assertThat(personTracking1).isNotEqualTo(personTracking2);
        personTracking1.setId(null);
        assertThat(personTracking1).isNotEqualTo(personTracking2);
    }
}
