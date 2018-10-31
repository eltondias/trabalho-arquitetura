package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.PlanoContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanoContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoContabilRepository extends JpaRepository<PlanoContabil, Long> {

}
