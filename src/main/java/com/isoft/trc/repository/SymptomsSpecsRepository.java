package com.isoft.trc.repository;

import com.isoft.trc.domain.SymptomsSpecs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SymptomsSpecs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SymptomsSpecsRepository extends JpaRepository<SymptomsSpecs, Long> {
}
