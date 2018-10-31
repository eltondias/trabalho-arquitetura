package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.DescontoPlanoContabil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DescontoPlanoContabil entity.
 */
public interface DescontoPlanoContabilSearchRepository extends ElasticsearchRepository<DescontoPlanoContabil, Long> {
}
