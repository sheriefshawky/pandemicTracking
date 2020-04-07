package com.isoft.trc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.trc.web.rest.TestUtil;

public class SymptomsSubDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SymptomsSubDetails.class);
        SymptomsSubDetails symptomsSubDetails1 = new SymptomsSubDetails();
        symptomsSubDetails1.setId(1L);
        SymptomsSubDetails symptomsSubDetails2 = new SymptomsSubDetails();
        symptomsSubDetails2.setId(symptomsSubDetails1.getId());
        assertThat(symptomsSubDetails1).isEqualTo(symptomsSubDetails2);
        symptomsSubDetails2.setId(2L);
        assertThat(symptomsSubDetails1).isNotEqualTo(symptomsSubDetails2);
        symptomsSubDetails1.setId(null);
        assertThat(symptomsSubDetails1).isNotEqualTo(symptomsSubDetails2);
    }
}
