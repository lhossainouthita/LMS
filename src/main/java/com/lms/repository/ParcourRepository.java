package com.lms.repository;

import com.lms.domain.Parcour;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Parcour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParcourRepository extends JpaRepository<Parcour, Long> {

    @Query("select parcour from Parcour parcour where parcour.tuteur.login = ?#{principal.username}")
    List<Parcour> findByTuteurIsCurrentUser();

}
