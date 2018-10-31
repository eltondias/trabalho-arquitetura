package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.GrupoContaAzul;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GrupoContaAzul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoContaAzulRepository extends JpaRepository<GrupoContaAzul, Long> {

}
