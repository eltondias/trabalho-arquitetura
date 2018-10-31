package com.arquitetira.trabalho.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PlanoEmpresaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PlanoEmpresaSearchRepositoryMockConfiguration {

    @MockBean
    private PlanoEmpresaSearchRepository mockPlanoEmpresaSearchRepository;

}
