package com.isoft.trc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.trc.web.rest.TestUtil;

public class PersonInteracitonTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInteracitonTracking.class);
        PersonInteracitonTracking personInteracitonTracking1 = new PersonInteracitonTracking();
        personInteracitonTracking1.setId(1L);
        PersonInteracitonTracking personInteracitonTracking2 = new PersonInteracitonTracking();
        personInteracitonTracking2.setId(personInteracitonTracking1.getId());
        assertThat(personInteracitonTracking1).isEqualTo(personInteracitonTracking2);
        personInteracitonTracking2.setId(2L);
        assertThat(personInteracitonTracking1).isNotEqualTo(personInteracitonTracking2);
        personInteracitonTracking1.setId(null);
        assertThat(personInteracitonTracking1).isNotEqualTo(personInteracitonTracking2);
    }
}
