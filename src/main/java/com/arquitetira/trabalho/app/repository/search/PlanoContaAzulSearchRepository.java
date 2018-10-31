package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.PlanoContaAzul;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PlanoContaAzul entity.
 */
public interface PlanoContaAzulSearchRepository extends ElasticsearchRepository<PlanoContaAzul, Long> {
}
