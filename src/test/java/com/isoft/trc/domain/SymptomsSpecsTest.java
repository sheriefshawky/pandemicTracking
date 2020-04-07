package com.isoft.trc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.trc.web.rest.TestUtil;

public class SymptomsSpecsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SymptomsSpecs.class);
        SymptomsSpecs symptomsSpecs1 = new SymptomsSpecs();
        symptomsSpecs1.setId(1L);
        SymptomsSpecs symptomsSpecs2 = new SymptomsSpecs();
        symptomsSpecs2.setId(symptomsSpecs1.getId());
        assertThat(symptomsSpecs1).isEqualTo(symptomsSpecs2);
        symptomsSpecs2.setId(2L);
        assertThat(symptomsSpecs1).isNotEqualTo(symptomsSpecs2);
        symptomsSpecs1.setId(null);
        assertThat(symptomsSpecs1).isNotEqualTo(symptomsSpecs2);
    }
}
