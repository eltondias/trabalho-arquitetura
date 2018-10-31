package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.PlanoEmpresa;
import com.arquitetira.trabalho.app.repository.PlanoEmpresaRepository;
import com.arquitetira.trabalho.app.repository.search.PlanoEmpresaSearchRepository;
import com.arquitetira.trabalho.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.arquitetira.trabalho.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PlanoEmpresaResource REST controller.
 *
 * @see PlanoEmpresaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class PlanoEmpresaResourceIntTest {

    private static final Long DEFAULT_FUNCIONARIOS = 1L;
    private static final Long UPDATED_FUNCIONARIOS = 2L;

    private static final Long DEFAULT_SOCIOS = 1L;
    private static final Long UPDATED_SOCIOS = 2L;

    private static final Long DEFAULT_FATURAMENTO_MENSAL = 1L;
    private static final Long UPDATED_FATURAMENTO_MENSAL = 2L;

    private static final String DEFAULT_DATA_CONTRATACAO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_CONTRATACAO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_ENCERRAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ENCERRAMENTO = "BBBBBBBBBB";

    @Autowired
    private PlanoEmpresaRepository planoEmpresaRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.PlanoEmpresaSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanoEmpresaSearchRepository mockPlanoEmpresaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanoEmpresaMockMvc;

    private PlanoEmpresa planoEmpresa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoEmpresaResource planoEmpresaResource = new PlanoEmpresaResource(planoEmpresaRepository, mockPlanoEmpresaSearchRepository);
        this.restPlanoEmpresaMockMvc = MockMvcBuilders.standaloneSetup(planoEmpresaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoEmpresa createEntity(EntityManager em) {
        PlanoEmpresa planoEmpresa = new PlanoEmpresa()
            .funcionarios(DEFAULT_FUNCIONARIOS)
            .socios(DEFAULT_SOCIOS)
            .faturamentoMensal(DEFAULT_FATURAMENTO_MENSAL)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .dataEncerramento(DEFAULT_DATA_ENCERRAMENTO);
        return planoEmpresa;
    }

    @Before
    public void initTest() {
        planoEmpresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoEmpresa() throws Exception {
        int databaseSizeBeforeCreate = planoEmpresaRepository.findAll().size();

        // Create the PlanoEmpresa
        restPlanoEmpresaMockMvc.perform(post("/api/plano-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoEmpresa)))
            .andExpect(status().isCreated());

        // Validate the PlanoEmpresa in the database
        List<PlanoEmpresa> planoEmpresaList = planoEmpresaRepository.findAll();
        assertThat(planoEmpresaList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoEmpresa testPlanoEmpresa = planoEmpresaList.get(planoEmpresaList.size() - 1);
        assertThat(testPlanoEmpresa.getFuncionarios()).isEqualTo(DEFAULT_FUNCIONARIOS);
        assertThat(testPlanoEmpresa.getSocios()).isEqualTo(DEFAULT_SOCIOS);
        assertThat(testPlanoEmpresa.getFaturamentoMensal()).isEqualTo(DEFAULT_FATURAMENTO_MENSAL);
        assertThat(testPlanoEmpresa.getDataContratacao()).isEqualTo(DEFAULT_DATA_CONTRATACAO);
        assertThat(testPlanoEmpresa.getDataEncerramento()).isEqualTo(DEFAULT_DATA_ENCERRAMENTO);

        // Validate the PlanoEmpresa in Elasticsearch
        verify(mockPlanoEmpresaSearchRepository, times(1)).save(testPlanoEmpresa);
    }

    @Test
    @Transactional
    public void createPlanoEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoEmpresaRepository.findAll().size();

        // Create the PlanoEmpresa with an existing ID
        planoEmpresa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoEmpresaMockMvc.perform(post("/api/plano-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoEmpresa in the database
        List<PlanoEmpresa> planoEmpresaList = planoEmpresaRepository.findAll();
        assertThat(planoEmpresaList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanoEmpresa in Elasticsearch
        verify(mockPlanoEmpresaSearchRepository, times(0)).save(planoEmpresa);
    }

    @Test
    @Transactional
    public void getAllPlanoEmpresas() throws Exception {
        // Initialize the database
        planoEmpresaRepository.saveAndFlush(planoEmpresa);

        // Get all the planoEmpresaList
        restPlanoEmpresaMockMvc.perform(get("/api/plano-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].funcionarios").value(hasItem(DEFAULT_FUNCIONARIOS.intValue())))
            .andExpect(jsonPath("$.[*].socios").value(hasItem(DEFAULT_SOCIOS.intValue())))
            .andExpect(jsonPath("$.[*].faturamentoMensal").value(hasItem(DEFAULT_FATURAMENTO_MENSAL.intValue())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoEmpresa() throws Exception {
        // Initialize the database
        planoEmpresaRepository.saveAndFlush(planoEmpresa);

        // Get the planoEmpresa
        restPlanoEmpresaMockMvc.perform(get("/api/plano-empresas/{id}", planoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.funcionarios").value(DEFAULT_FUNCIONARIOS.intValue()))
            .andExpect(jsonPath("$.socios").value(DEFAULT_SOCIOS.intValue()))
            .andExpect(jsonPath("$.faturamentoMensal").value(DEFAULT_FATURAMENTO_MENSAL.intValue()))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.dataEncerramento").value(DEFAULT_DATA_ENCERRAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoEmpresa() throws Exception {
        // Get the planoEmpresa
        restPlanoEmpresaMockMvc.perform(get("/api/plano-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoEmpresa() throws Exception {
        // Initialize the database
        planoEmpresaRepository.saveAndFlush(planoEmpresa);

        int databaseSizeBeforeUpdate = planoEmpresaRepository.findAll().size();

        // Update the planoEmpresa
        PlanoEmpresa updatedPlanoEmpresa = planoEmpresaRepository.findById(planoEmpresa.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoEmpresa are not directly saved in db
        em.detach(updatedPlanoEmpresa);
        updatedPlanoEmpresa
            .funcionarios(UPDATED_FUNCIONARIOS)
            .socios(UPDATED_SOCIOS)
            .faturamentoMensal(UPDATED_FATURAMENTO_MENSAL)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO);

        restPlanoEmpresaMockMvc.perform(put("/api/plano-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanoEmpresa)))
            .andExpect(status().isOk());

        // Validate the PlanoEmpresa in the database
        List<PlanoEmpresa> planoEmpresaList = planoEmpresaRepository.findAll();
        assertThat(planoEmpresaList).hasSize(databaseSizeBeforeUpdate);
        PlanoEmpresa testPlanoEmpresa = planoEmpresaList.get(planoEmpresaList.size() - 1);
        assertThat(testPlanoEmpresa.getFuncionarios()).isEqualTo(UPDATED_FUNCIONARIOS);
        assertThat(testPlanoEmpresa.getSocios()).isEqualTo(UPDATED_SOCIOS);
        assertThat(testPlanoEmpresa.getFaturamentoMensal()).isEqualTo(UPDATED_FATURAMENTO_MENSAL);
        assertThat(testPlanoEmpresa.getDataContratacao()).isEqualTo(UPDATED_DATA_CONTRATACAO);
        assertThat(testPlanoEmpresa.getDataEncerramento()).isEqualTo(UPDATED_DATA_ENCERRAMENTO);

        // Validate the PlanoEmpresa in Elasticsearch
        verify(mockPlanoEmpresaSearchRepository, times(1)).save(testPlanoEmpresa);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = planoEmpresaRepository.findAll().size();

        // Create the PlanoEmpresa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoEmpresaMockMvc.perform(put("/api/plano-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoEmpresa in the database
        List<PlanoEmpresa> planoEmpresaList = planoEmpresaRepository.findAll();
        assertThat(planoEmpresaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanoEmpresa in Elasticsearch
        verify(mockPlanoEmpresaSearchRepository, times(0)).save(planoEmpresa);
    }

    @Test
    @Transactional
    public void deletePlanoEmpresa() throws Exception {
        // Initialize the database
        planoEmpresaRepository.saveAndFlush(planoEmpresa);

        int databaseSizeBeforeDelete = planoEmpresaRepository.findAll().size();

        // Get the planoEmpresa
        restPlanoEmpresaMockMvc.perform(delete("/api/plano-empresas/{id}", planoEmpresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanoEmpresa> planoEmpresaList = planoEmpresaRepository.findAll();
        assertThat(planoEmpresaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanoEmpresa in Elasticsearch
        verify(mockPlanoEmpresaSearchRepository, times(1)).deleteById(planoEmpresa.getId());
    }

    @Test
    @Transactional
    public void searchPlanoEmpresa() throws Exception {
        // Initialize the database
        planoEmpresaRepository.saveAndFlush(planoEmpresa);
        when(mockPlanoEmpresaSearchRepository.search(queryStringQuery("id:" + planoEmpresa.getId())))
            .thenReturn(Collections.singletonList(planoEmpresa));
        // Search the planoEmpresa
        restPlanoEmpresaMockMvc.perform(get("/api/_search/plano-empresas?query=id:" + planoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].funcionarios").value(hasItem(DEFAULT_FUNCIONARIOS.intValue())))
            .andExpect(jsonPath("$.[*].socios").value(hasItem(DEFAULT_SOCIOS.intValue())))
            .andExpect(jsonPath("$.[*].faturamentoMensal").value(hasItem(DEFAULT_FATURAMENTO_MENSAL.intValue())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoEmpresa.class);
        PlanoEmpresa planoEmpresa1 = new PlanoEmpresa();
        planoEmpresa1.setId(1L);
        PlanoEmpresa planoEmpresa2 = new PlanoEmpresa();
        planoEmpresa2.setId(planoEmpresa1.getId());
        assertThat(planoEmpresa1).isEqualTo(planoEmpresa2);
        planoEmpresa2.setId(2L);
        assertThat(planoEmpresa1).isNotEqualTo(planoEmpresa2);
        planoEmpresa1.setId(null);
        assertThat(planoEmpresa1).isNotEqualTo(planoEmpresa2);
    }
}
