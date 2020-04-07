package com.isoft.trc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A PersonInteracitonTracking.
 */
@Entity
@Table(name = "person_interaciton_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonInteracitonTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "interacted_user_id", nullable = false)
    private Long interactedUserId;

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

    public PersonInteracitonTracking userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInteractedUserId() {
        return interactedUserId;
    }

    public PersonInteracitonTracking interactedUserId(Long interactedUserId) {
        this.interactedUserId = interactedUserId;
        return this;
    }

    public void setInteractedUserId(Long interactedUserId) {
        this.interactedUserId = interactedUserId;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public PersonInteracitonTracking locationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
        return this;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public PersonInteracitonTracking locationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
        return this;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Instant getLocationTime() {
        return locationTime;
    }

    public PersonInteracitonTracking locationTime(Instant locationTime) {
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
        if (!(o instanceof PersonInteracitonTracking)) {
            return false;
        }
        return id != null && id.equals(((PersonInteracitonTracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonInteracitonTracking{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", interactedUserId=" + getInteractedUserId() +
            ", locationLongitude=" + getLocationLongitude() +
            ", locationLatitude=" + getLocationLatitude() +
            ", locationTime='" + getLocationTime() + "'" +
            "}";
    }
}
