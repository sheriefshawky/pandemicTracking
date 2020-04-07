package com.isoft.trc.repository;

import com.isoft.trc.domain.PersonInteracitonTracking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PersonInteracitonTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInteracitonTrackingRepository extends JpaRepository<PersonInteracitonTracking, Long> {
}
