package com.isoft.trc.repository;

import com.isoft.trc.domain.SymptomsSubDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SymptomsSubDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SymptomsSubDetailsRepository extends JpaRepository<SymptomsSubDetails, Long> {
}
