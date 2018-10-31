package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.PlanoContabil;
import com.arquitetira.trabalho.app.repository.PlanoContabilRepository;
import com.arquitetira.trabalho.app.repository.search.PlanoContabilSearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the PlanoContabilResource REST controller.
 *
 * @see PlanoContabilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class PlanoContabilResourceIntTest {

    private static final Long DEFAULT_FUNCIONARIOS = 1L;
    private static final Long UPDATED_FUNCIONARIOS = 2L;

    private static final Long DEFAULT_SOCIOS = 1L;
    private static final Long UPDATED_SOCIOS = 2L;

    private static final Long DEFAULT_FATURAMENTO_MENSAL = 1L;
    private static final Long UPDATED_FATURAMENTO_MENSAL = 2L;

    private static final Instant DEFAULT_DATA_CONTRATACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONTRATACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ENCERRAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENCERRAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PlanoContabilRepository planoContabilRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.PlanoContabilSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanoContabilSearchRepository mockPlanoContabilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanoContabilMockMvc;

    private PlanoContabil planoContabil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoContabilResource planoContabilResource = new PlanoContabilResource(planoContabilRepository, mockPlanoContabilSearchRepository);
        this.restPlanoContabilMockMvc = MockMvcBuilders.standaloneSetup(planoContabilResource)
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
    public static PlanoContabil createEntity(EntityManager em) {
        PlanoContabil planoContabil = new PlanoContabil()
            .funcionarios(DEFAULT_FUNCIONARIOS)
            .socios(DEFAULT_SOCIOS)
            .faturamentoMensal(DEFAULT_FATURAMENTO_MENSAL)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .dataEncerramento(DEFAULT_DATA_ENCERRAMENTO);
        return planoContabil;
    }

    @Before
    public void initTest() {
        planoContabil = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoContabil() throws Exception {
        int databaseSizeBeforeCreate = planoContabilRepository.findAll().size();

        // Create the PlanoContabil
        restPlanoContabilMockMvc.perform(post("/api/plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoContabil)))
            .andExpect(status().isCreated());

        // Validate the PlanoContabil in the database
        List<PlanoContabil> planoContabilList = planoContabilRepository.findAll();
        assertThat(planoContabilList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoContabil testPlanoContabil = planoContabilList.get(planoContabilList.size() - 1);
        assertThat(testPlanoContabil.getFuncionarios()).isEqualTo(DEFAULT_FUNCIONARIOS);
        assertThat(testPlanoContabil.getSocios()).isEqualTo(DEFAULT_SOCIOS);
        assertThat(testPlanoContabil.getFaturamentoMensal()).isEqualTo(DEFAULT_FATURAMENTO_MENSAL);
        assertThat(testPlanoContabil.getDataContratacao()).isEqualTo(DEFAULT_DATA_CONTRATACAO);
        assertThat(testPlanoContabil.getDataEncerramento()).isEqualTo(DEFAULT_DATA_ENCERRAMENTO);

        // Validate the PlanoContabil in Elasticsearch
        verify(mockPlanoContabilSearchRepository, times(1)).save(testPlanoContabil);
    }

    @Test
    @Transactional
    public void createPlanoContabilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoContabilRepository.findAll().size();

        // Create the PlanoContabil with an existing ID
        planoContabil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoContabilMockMvc.perform(post("/api/plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoContabil)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        List<PlanoContabil> planoContabilList = planoContabilRepository.findAll();
        assertThat(planoContabilList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanoContabil in Elasticsearch
        verify(mockPlanoContabilSearchRepository, times(0)).save(planoContabil);
    }

    @Test
    @Transactional
    public void getAllPlanoContabils() throws Exception {
        // Initialize the database
        planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList
        restPlanoContabilMockMvc.perform(get("/api/plano-contabils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].funcionarios").value(hasItem(DEFAULT_FUNCIONARIOS.intValue())))
            .andExpect(jsonPath("$.[*].socios").value(hasItem(DEFAULT_SOCIOS.intValue())))
            .andExpect(jsonPath("$.[*].faturamentoMensal").value(hasItem(DEFAULT_FATURAMENTO_MENSAL.intValue())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanoContabil() throws Exception {
        // Initialize the database
        planoContabilRepository.saveAndFlush(planoContabil);

        // Get the planoContabil
        restPlanoContabilMockMvc.perform(get("/api/plano-contabils/{id}", planoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoContabil.getId().intValue()))
            .andExpect(jsonPath("$.funcionarios").value(DEFAULT_FUNCIONARIOS.intValue()))
            .andExpect(jsonPath("$.socios").value(DEFAULT_SOCIOS.intValue()))
            .andExpect(jsonPath("$.faturamentoMensal").value(DEFAULT_FATURAMENTO_MENSAL.intValue()))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.dataEncerramento").value(DEFAULT_DATA_ENCERRAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoContabil() throws Exception {
        // Get the planoContabil
        restPlanoContabilMockMvc.perform(get("/api/plano-contabils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoContabil() throws Exception {
        // Initialize the database
        planoContabilRepository.saveAndFlush(planoContabil);

        int databaseSizeBeforeUpdate = planoContabilRepository.findAll().size();

        // Update the planoContabil
        PlanoContabil updatedPlanoContabil = planoContabilRepository.findById(planoContabil.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoContabil are not directly saved in db
        em.detach(updatedPlanoContabil);
        updatedPlanoContabil
            .funcionarios(UPDATED_FUNCIONARIOS)
            .socios(UPDATED_SOCIOS)
            .faturamentoMensal(UPDATED_FATURAMENTO_MENSAL)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO);

        restPlanoContabilMockMvc.perform(put("/api/plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanoContabil)))
            .andExpect(status().isOk());

        // Validate the PlanoContabil in the database
        List<PlanoContabil> planoContabilList = planoContabilRepository.findAll();
        assertThat(planoContabilList).hasSize(databaseSizeBeforeUpdate);
        PlanoContabil testPlanoContabil = planoContabilList.get(planoContabilList.size() - 1);
        assertThat(testPlanoContabil.getFuncionarios()).isEqualTo(UPDATED_FUNCIONARIOS);
        assertThat(testPlanoContabil.getSocios()).isEqualTo(UPDATED_SOCIOS);
        assertThat(testPlanoContabil.getFaturamentoMensal()).isEqualTo(UPDATED_FATURAMENTO_MENSAL);
        assertThat(testPlanoContabil.getDataContratacao()).isEqualTo(UPDATED_DATA_CONTRATACAO);
        assertThat(testPlanoContabil.getDataEncerramento()).isEqualTo(UPDATED_DATA_ENCERRAMENTO);

        // Validate the PlanoContabil in Elasticsearch
        verify(mockPlanoContabilSearchRepository, times(1)).save(testPlanoContabil);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoContabil() throws Exception {
        int databaseSizeBeforeUpdate = planoContabilRepository.findAll().size();

        // Create the PlanoContabil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc.perform(put("/api/plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoContabil)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        List<PlanoContabil> planoContabilList = planoContabilRepository.findAll();
        assertThat(planoContabilList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanoContabil in Elasticsearch
        verify(mockPlanoContabilSearchRepository, times(0)).save(planoContabil);
    }

    @Test
    @Transactional
    public void deletePlanoContabil() throws Exception {
        // Initialize the database
        planoContabilRepository.saveAndFlush(planoContabil);

        int databaseSizeBeforeDelete = planoContabilRepository.findAll().size();

        // Get the planoContabil
        restPlanoContabilMockMvc.perform(delete("/api/plano-contabils/{id}", planoContabil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanoContabil> planoContabilList = planoContabilRepository.findAll();
        assertThat(planoContabilList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanoContabil in Elasticsearch
        verify(mockPlanoContabilSearchRepository, times(1)).deleteById(planoContabil.getId());
    }

    @Test
    @Transactional
    public void searchPlanoContabil() throws Exception {
        // Initialize the database
        planoContabilRepository.saveAndFlush(planoContabil);
        when(mockPlanoContabilSearchRepository.search(queryStringQuery("id:" + planoContabil.getId())))
            .thenReturn(Collections.singletonList(planoContabil));
        // Search the planoContabil
        restPlanoContabilMockMvc.perform(get("/api/_search/plano-contabils?query=id:" + planoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].funcionarios").value(hasItem(DEFAULT_FUNCIONARIOS.intValue())))
            .andExpect(jsonPath("$.[*].socios").value(hasItem(DEFAULT_SOCIOS.intValue())))
            .andExpect(jsonPath("$.[*].faturamentoMensal").value(hasItem(DEFAULT_FATURAMENTO_MENSAL.intValue())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoContabil.class);
        PlanoContabil planoContabil1 = new PlanoContabil();
        planoContabil1.setId(1L);
        PlanoContabil planoContabil2 = new PlanoContabil();
        planoContabil2.setId(planoContabil1.getId());
        assertThat(planoContabil1).isEqualTo(planoContabil2);
        planoContabil2.setId(2L);
        assertThat(planoContabil1).isNotEqualTo(planoContabil2);
        planoContabil1.setId(null);
        assertThat(planoContabil1).isNotEqualTo(planoContabil2);
    }
}
