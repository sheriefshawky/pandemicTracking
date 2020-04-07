package com.isoft.trc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SymptomsSubDetails.
 */
@Entity
@Table(name = "symptoms_sub_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SymptomsSubDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @OneToOne
    @JoinColumn(unique = true)
    private SymptomsSpecs symptomSpec;

    @ManyToOne
    @JsonIgnoreProperties("subDetails")
    private SymptomsSumbission symptomsSumbission;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public SymptomsSubDetails value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SymptomsSpecs getSymptomSpec() {
        return symptomSpec;
    }

    public SymptomsSubDetails symptomSpec(SymptomsSpecs symptomsSpecs) {
        this.symptomSpec = symptomsSpecs;
        return this;
    }

    public void setSymptomSpec(SymptomsSpecs symptomsSpecs) {
        this.symptomSpec = symptomsSpecs;
    }

    public SymptomsSumbission getSymptomsSumbission() {
        return symptomsSumbission;
    }

    public SymptomsSubDetails symptomsSumbission(SymptomsSumbission symptomsSumbission) {
        this.symptomsSumbission = symptomsSumbission;
        return this;
    }

    public void setSymptomsSumbission(SymptomsSumbission symptomsSumbission) {
        this.symptomsSumbission = symptomsSumbission;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SymptomsSubDetails)) {
            return false;
        }
        return id != null && id.equals(((SymptomsSubDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SymptomsSubDetails{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
