package com.lms.repository;

import com.lms.domain.Devoir;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Devoir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevoirRepository extends JpaRepository<Devoir, Long> {

}
