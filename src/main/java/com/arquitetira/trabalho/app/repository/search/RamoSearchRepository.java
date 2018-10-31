package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.Ramo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ramo entity.
 */
public interface RamoSearchRepository extends ElasticsearchRepository<Ramo, Long> {
}
