package com.isoft.trc.repository;

import com.isoft.trc.domain.PersonTracking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PersonTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonTrackingRepository extends JpaRepository<PersonTracking, Long> {
}
