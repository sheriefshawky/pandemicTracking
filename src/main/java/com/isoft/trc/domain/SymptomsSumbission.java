package com.isoft.trc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A SymptomsSumbission.
 */
@Entity
@Table(name = "symptoms_sumbission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SymptomsSumbission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "submission_time", nullable = false)
    private Instant submissionTime;

    @OneToMany(mappedBy = "symptomsSumbission")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SymptomsSubDetails> subDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public SymptomsSumbission userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getSubmissionTime() {
        return submissionTime;
    }

    public SymptomsSumbission submissionTime(Instant submissionTime) {
        this.submissionTime = submissionTime;
        return this;
    }

    public void setSubmissionTime(Instant submissionTime) {
        this.submissionTime = submissionTime;
    }

    public Set<SymptomsSubDetails> getSubDetails() {
        return subDetails;
    }

    public SymptomsSumbission subDetails(Set<SymptomsSubDetails> symptomsSubDetails) {
        this.subDetails = symptomsSubDetails;
        return this;
    }

    public SymptomsSumbission addSubDetails(SymptomsSubDetails symptomsSubDetails) {
        this.subDetails.add(symptomsSubDetails);
        symptomsSubDetails.setSymptomsSumbission(this);
        return this;
    }

    public SymptomsSumbission removeSubDetails(SymptomsSubDetails symptomsSubDetails) {
        this.subDetails.remove(symptomsSubDetails);
        symptomsSubDetails.setSymptomsSumbission(null);
        return this;
    }

    public void setSubDetails(Set<SymptomsSubDetails> symptomsSubDetails) {
        this.subDetails = symptomsSubDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SymptomsSumbission)) {
            return false;
        }
        return id != null && id.equals(((SymptomsSumbission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SymptomsSumbission{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", submissionTime='" + getSubmissionTime() + "'" +
            "}";
    }
}
