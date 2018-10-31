package com.arquitetira.trabalho.app.repository;

import com.arquitetira.trabalho.app.domain.PeriodoPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PeriodoPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoPagamentoRepository extends JpaRepository<PeriodoPagamento, Long> {

}
