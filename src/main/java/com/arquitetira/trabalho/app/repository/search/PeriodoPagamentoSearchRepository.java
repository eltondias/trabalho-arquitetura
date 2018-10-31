package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.PeriodoPagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PeriodoPagamento entity.
 */
public interface PeriodoPagamentoSearchRepository extends ElasticsearchRepository<PeriodoPagamento, Long> {
}
