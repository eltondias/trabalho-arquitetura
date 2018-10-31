package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.PlanoContaAzul;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanoContaAzul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoContaAzulRepository extends JpaRepository<PlanoContaAzul, Long> {

}
