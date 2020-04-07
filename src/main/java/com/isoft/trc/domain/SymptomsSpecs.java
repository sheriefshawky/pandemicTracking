package com.isoft.trc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SymptomsSpecs.
 */
@Entity
@Table(name = "symptoms_specs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SymptomsSpecs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "description_ar", nullable = false)
    private String descriptionAr;

    @NotNull
    @Column(name = "description_en", nullable = false)
    private String descriptionEn;

    @NotNull
    @Column(name = "spec_type", nullable = false)
    private Integer specType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public SymptomsSpecs code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public SymptomsSpecs descriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
        return this;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public SymptomsSpecs descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public Integer getSpecType() {
        return specType;
    }

    public SymptomsSpecs specType(Integer specType) {
        this.specType = specType;
        return this;
    }

    public void setSpecType(Integer specType) {
        this.specType = specType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SymptomsSpecs)) {
            return false;
        }
        return id != null && id.equals(((SymptomsSpecs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SymptomsSpecs{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", descriptionAr='" + getDescriptionAr() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", specType=" + getSpecType() +
            "}";
    }
}
