package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.Enquadramento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Enquadramento entity.
 */
public interface EnquadramentoSearchRepository extends ElasticsearchRepository<Enquadramento, Long> {
}
