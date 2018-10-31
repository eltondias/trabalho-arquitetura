package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.PeriodoPagamento;
import com.arquitetira.trabalho.app.repository.PeriodoPagamentoRepository;
import com.arquitetira.trabalho.app.repository.search.PeriodoPagamentoSearchRepository;
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
 * Test class for the PeriodoPagamentoResource REST controller.
 *
 * @see PeriodoPagamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class PeriodoPagamentoResourceIntTest {

    private static final String DEFAULT_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO = "BBBBBBBBBB";

    @Autowired
    private PeriodoPagamentoRepository periodoPagamentoRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.PeriodoPagamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PeriodoPagamentoSearchRepository mockPeriodoPagamentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodoPagamentoMockMvc;

    private PeriodoPagamento periodoPagamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodoPagamentoResource periodoPagamentoResource = new PeriodoPagamentoResource(periodoPagamentoRepository, mockPeriodoPagamentoSearchRepository);
        this.restPeriodoPagamentoMockMvc = MockMvcBuilders.standaloneSetup(periodoPagamentoResource)
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
    public static PeriodoPagamento createEntity(EntityManager em) {
        PeriodoPagamento periodoPagamento = new PeriodoPagamento()
            .periodo(DEFAULT_PERIODO);
        return periodoPagamento;
    }

    @Before
    public void initTest() {
        periodoPagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodoPagamento() throws Exception {
        int databaseSizeBeforeCreate = periodoPagamentoRepository.findAll().size();

        // Create the PeriodoPagamento
        restPeriodoPagamentoMockMvc.perform(post("/api/periodo-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoPagamento)))
            .andExpect(status().isCreated());

        // Validate the PeriodoPagamento in the database
        List<PeriodoPagamento> periodoPagamentoList = periodoPagamentoRepository.findAll();
        assertThat(periodoPagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodoPagamento testPeriodoPagamento = periodoPagamentoList.get(periodoPagamentoList.size() - 1);
        assertThat(testPeriodoPagamento.getPeriodo()).isEqualTo(DEFAULT_PERIODO);

        // Validate the PeriodoPagamento in Elasticsearch
        verify(mockPeriodoPagamentoSearchRepository, times(1)).save(testPeriodoPagamento);
    }

    @Test
    @Transactional
    public void createPeriodoPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodoPagamentoRepository.findAll().size();

        // Create the PeriodoPagamento with an existing ID
        periodoPagamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoPagamentoMockMvc.perform(post("/api/periodo-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoPagamento)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        List<PeriodoPagamento> periodoPagamentoList = periodoPagamentoRepository.findAll();
        assertThat(periodoPagamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PeriodoPagamento in Elasticsearch
        verify(mockPeriodoPagamentoSearchRepository, times(0)).save(periodoPagamento);
    }

    @Test
    @Transactional
    public void getAllPeriodoPagamentos() throws Exception {
        // Initialize the database
        periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList
        restPeriodoPagamentoMockMvc.perform(get("/api/periodo-pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriodoPagamento() throws Exception {
        // Initialize the database
        periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get the periodoPagamento
        restPeriodoPagamentoMockMvc.perform(get("/api/periodo-pagamentos/{id}", periodoPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodoPagamento.getId().intValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodoPagamento() throws Exception {
        // Get the periodoPagamento
        restPeriodoPagamentoMockMvc.perform(get("/api/periodo-pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodoPagamento() throws Exception {
        // Initialize the database
        periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        int databaseSizeBeforeUpdate = periodoPagamentoRepository.findAll().size();

        // Update the periodoPagamento
        PeriodoPagamento updatedPeriodoPagamento = periodoPagamentoRepository.findById(periodoPagamento.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodoPagamento are not directly saved in db
        em.detach(updatedPeriodoPagamento);
        updatedPeriodoPagamento
            .periodo(UPDATED_PERIODO);

        restPeriodoPagamentoMockMvc.perform(put("/api/periodo-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodoPagamento)))
            .andExpect(status().isOk());

        // Validate the PeriodoPagamento in the database
        List<PeriodoPagamento> periodoPagamentoList = periodoPagamentoRepository.findAll();
        assertThat(periodoPagamentoList).hasSize(databaseSizeBeforeUpdate);
        PeriodoPagamento testPeriodoPagamento = periodoPagamentoList.get(periodoPagamentoList.size() - 1);
        assertThat(testPeriodoPagamento.getPeriodo()).isEqualTo(UPDATED_PERIODO);

        // Validate the PeriodoPagamento in Elasticsearch
        verify(mockPeriodoPagamentoSearchRepository, times(1)).save(testPeriodoPagamento);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodoPagamento() throws Exception {
        int databaseSizeBeforeUpdate = periodoPagamentoRepository.findAll().size();

        // Create the PeriodoPagamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc.perform(put("/api/periodo-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodoPagamento)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        List<PeriodoPagamento> periodoPagamentoList = periodoPagamentoRepository.findAll();
        assertThat(periodoPagamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PeriodoPagamento in Elasticsearch
        verify(mockPeriodoPagamentoSearchRepository, times(0)).save(periodoPagamento);
    }

    @Test
    @Transactional
    public void deletePeriodoPagamento() throws Exception {
        // Initialize the database
        periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        int databaseSizeBeforeDelete = periodoPagamentoRepository.findAll().size();

        // Get the periodoPagamento
        restPeriodoPagamentoMockMvc.perform(delete("/api/periodo-pagamentos/{id}", periodoPagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeriodoPagamento> periodoPagamentoList = periodoPagamentoRepository.findAll();
        assertThat(periodoPagamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PeriodoPagamento in Elasticsearch
        verify(mockPeriodoPagamentoSearchRepository, times(1)).deleteById(periodoPagamento.getId());
    }

    @Test
    @Transactional
    public void searchPeriodoPagamento() throws Exception {
        // Initialize the database
        periodoPagamentoRepository.saveAndFlush(periodoPagamento);
        when(mockPeriodoPagamentoSearchRepository.search(queryStringQuery("id:" + periodoPagamento.getId())))
            .thenReturn(Collections.singletonList(periodoPagamento));
        // Search the periodoPagamento
        restPeriodoPagamentoMockMvc.perform(get("/api/_search/periodo-pagamentos?query=id:" + periodoPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoPagamento.class);
        PeriodoPagamento periodoPagamento1 = new PeriodoPagamento();
        periodoPagamento1.setId(1L);
        PeriodoPagamento periodoPagamento2 = new PeriodoPagamento();
        periodoPagamento2.setId(periodoPagamento1.getId());
        assertThat(periodoPagamento1).isEqualTo(periodoPagamento2);
        periodoPagamento2.setId(2L);
        assertThat(periodoPagamento1).isNotEqualTo(periodoPagamento2);
        periodoPagamento1.setId(null);
        assertThat(periodoPagamento1).isNotEqualTo(periodoPagamento2);
    }
}
