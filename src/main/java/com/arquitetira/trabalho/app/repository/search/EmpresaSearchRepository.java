package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.Empresa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Empresa entity.
 */
public interface EmpresaSearchRepository extends ElasticsearchRepository<Empresa, Long> {
}
