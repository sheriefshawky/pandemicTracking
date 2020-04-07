package com.isoft.trc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.trc.web.rest.TestUtil;

public class SymptomsSumbissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SymptomsSumbission.class);
        SymptomsSumbission symptomsSumbission1 = new SymptomsSumbission();
        symptomsSumbission1.setId(1L);
        SymptomsSumbission symptomsSumbission2 = new SymptomsSumbission();
        symptomsSumbission2.setId(symptomsSumbission1.getId());
        assertThat(symptomsSumbission1).isEqualTo(symptomsSumbission2);
        symptomsSumbission2.setId(2L);
        assertThat(symptomsSumbission1).isNotEqualTo(symptomsSumbission2);
        symptomsSumbission1.setId(null);
        assertThat(symptomsSumbission1).isNotEqualTo(symptomsSumbission2);
    }
}
