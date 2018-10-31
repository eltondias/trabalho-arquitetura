package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.PlanoContabil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PlanoContabil entity.
 */
public interface PlanoContabilSearchRepository extends ElasticsearchRepository<PlanoContabil, Long> {
}
