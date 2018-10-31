package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.DescontoGrupo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DescontoGrupo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DescontoGrupoRepository extends JpaRepository<DescontoGrupo, Long> {

}
