package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.PlanoEmpresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanoEmpresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoEmpresaRepository extends JpaRepository<PlanoEmpresa, Long> {

}
