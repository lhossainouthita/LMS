package com.lms.repository;

import com.lms.domain.ModulePedagogique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ModulePedagogique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModulePedagogiqueRepository extends JpaRepository<ModulePedagogique, Long> {

    @Query("select modulePedagogique from ModulePedagogique modulePedagogique where modulePedagogique.admin.login = ?#{principal.username}")
    List<ModulePedagogique> findByAdminIsCurrentUser();

}
