package com.arquitetira.trabalho.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DescontoPlanoContabilSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DescontoPlanoContabilSearchRepositoryMockConfiguration {

    @MockBean
    private DescontoPlanoContabilSearchRepository mockDescontoPlanoContabilSearchRepository;

}
