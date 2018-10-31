package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.DescontoPlanoContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DescontoPlanoContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescontoPlanoContabilRepository extends JpaRepository<DescontoPlanoContabil, Long> {

}
