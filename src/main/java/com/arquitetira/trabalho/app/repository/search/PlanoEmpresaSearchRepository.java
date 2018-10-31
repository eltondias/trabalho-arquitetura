package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.PlanoEmpresa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PlanoEmpresa entity.
 */
public interface PlanoEmpresaSearchRepository extends ElasticsearchRepository<PlanoEmpresa, Long> {
}
