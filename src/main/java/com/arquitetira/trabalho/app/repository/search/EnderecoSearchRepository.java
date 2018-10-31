package com.arquitetira.trabalho.app.repository.search;

import com.arquitetira.trabalho.app.domain.Endereco;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Endereco entity.
 */
public interface EnderecoSearchRepository extends ElasticsearchRepository<Endereco, Long> {
}
