package com.lms.repository;

import com.lms.domain.Sujet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sujet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SujetRepository extends JpaRepository<Sujet, Long> {

}
