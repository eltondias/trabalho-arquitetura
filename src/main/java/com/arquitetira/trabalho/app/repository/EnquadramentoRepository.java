package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.Enquadramento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enquadramento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnquadramentoRepository extends JpaRepository<Enquadramento, Long> {

}
