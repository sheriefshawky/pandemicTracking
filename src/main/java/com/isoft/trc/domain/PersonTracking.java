package com.isoft.trc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A PersonTracking.
 */
@Entity
@Table(name = "person_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "location_longitude", nullable = false)
    private Double locationLongitude;

    @NotNull
    @Column(name = "location_latitude", nullable = false)
    private Double locationLatitude;

    @Column(name = "location_time")
    private Instant locationTime;

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

    public PersonTracking userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public PersonTracking locationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
        return this;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public PersonTracking locationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
        return this;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Instant getLocationTime() {
        return locationTime;
    }

    public PersonTracking locationTime(Instant locationTime) {
        this.locationTime = locationTime;
        return this;
    }

    public void setLocationTime(Instant locationTime) {
        this.locationTime = locationTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonTracking)) {
            return false;
        }
        return id != null && id.equals(((PersonTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonTracking{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", locationLongitude=" + getLocationLongitude() +
            ", locationLatitude=" + getLocationLatitude() +
            ", locationTime='" + getLocationTime() + "'" +
            "}";
    }
}
