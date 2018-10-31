package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.GrupoContaAzul;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GrupoContaAzul entity.
 */
public interface GrupoContaAzulSearchRepository extends ElasticsearchRepository<GrupoContaAzul, Long> {
}
