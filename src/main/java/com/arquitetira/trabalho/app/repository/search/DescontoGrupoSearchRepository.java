package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.DescontoGrupo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DescontoGrupo entity.
 */
public interface DescontoGrupoSearchRepository extends ElasticsearchRepository<DescontoGrupo, Long> {
}
