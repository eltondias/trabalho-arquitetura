package com.arquitetira.trabalho.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of GrupoContaAzulSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GrupoContaAzulSearchRepositoryMockConfiguration {

    @MockBean
    private GrupoContaAzulSearchRepository mockGrupoContaAzulSearchRepository;

}
