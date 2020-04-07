package com.isoft.trc.repository;

import com.isoft.trc.domain.SymptomsSumbission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SymptomsSumbission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SymptomsSumbissionRepository extends JpaRepository<SymptomsSumbission, Long> {
}
