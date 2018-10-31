package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.Enquadramento;
import com.arquitetira.trabalho.app.repository.EnquadramentoRepository;
import com.arquitetira.trabalho.app.repository.search.EnquadramentoSearchRepository;
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
 * Test class for the EnquadramentoResource REST controller.
 *
 * @see EnquadramentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class EnquadramentoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Long DEFAULT_LIMITE = 1L;
    private static final Long UPDATED_LIMITE = 2L;

    @Autowired
    private EnquadramentoRepository enquadramentoRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.EnquadramentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EnquadramentoSearchRepository mockEnquadramentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnquadramentoMockMvc;

    private Enquadramento enquadramento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnquadramentoResource enquadramentoResource = new EnquadramentoResource(enquadramentoRepository, mockEnquadramentoSearchRepository);
        this.restEnquadramentoMockMvc = MockMvcBuilders.standaloneSetup(enquadramentoResource)
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
    public static Enquadramento createEntity(EntityManager em) {
        Enquadramento enquadramento = new Enquadramento()
            .nome(DEFAULT_NOME)
            .limite(DEFAULT_LIMITE);
        return enquadramento;
    }

    @Before
    public void initTest() {
        enquadramento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquadramento() throws Exception {
        int databaseSizeBeforeCreate = enquadramentoRepository.findAll().size();

        // Create the Enquadramento
        restEnquadramentoMockMvc.perform(post("/api/enquadramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enquadramento)))
            .andExpect(status().isCreated());

        // Validate the Enquadramento in the database
        List<Enquadramento> enquadramentoList = enquadramentoRepository.findAll();
        assertThat(enquadramentoList).hasSize(databaseSizeBeforeCreate + 1);
        Enquadramento testEnquadramento = enquadramentoList.get(enquadramentoList.size() - 1);
        assertThat(testEnquadramento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEnquadramento.getLimite()).isEqualTo(DEFAULT_LIMITE);

        // Validate the Enquadramento in Elasticsearch
        verify(mockEnquadramentoSearchRepository, times(1)).save(testEnquadramento);
    }

    @Test
    @Transactional
    public void createEnquadramentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enquadramentoRepository.findAll().size();

        // Create the Enquadramento with an existing ID
        enquadramento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquadramentoMockMvc.perform(post("/api/enquadramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enquadramento)))
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        List<Enquadramento> enquadramentoList = enquadramentoRepository.findAll();
        assertThat(enquadramentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Enquadramento in Elasticsearch
        verify(mockEnquadramentoSearchRepository, times(0)).save(enquadramento);
    }

    @Test
    @Transactional
    public void getAllEnquadramentos() throws Exception {
        // Initialize the database
        enquadramentoRepository.saveAndFlush(enquadramento);

        // Get all the enquadramentoList
        restEnquadramentoMockMvc.perform(get("/api/enquadramentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquadramento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].limite").value(hasItem(DEFAULT_LIMITE.intValue())));
    }
    
    @Test
    @Transactional
    public void getEnquadramento() throws Exception {
        // Initialize the database
        enquadramentoRepository.saveAndFlush(enquadramento);

        // Get the enquadramento
        restEnquadramentoMockMvc.perform(get("/api/enquadramentos/{id}", enquadramento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enquadramento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.limite").value(DEFAULT_LIMITE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEnquadramento() throws Exception {
        // Get the enquadramento
        restEnquadramentoMockMvc.perform(get("/api/enquadramentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquadramento() throws Exception {
        // Initialize the database
        enquadramentoRepository.saveAndFlush(enquadramento);

        int databaseSizeBeforeUpdate = enquadramentoRepository.findAll().size();

        // Update the enquadramento
        Enquadramento updatedEnquadramento = enquadramentoRepository.findById(enquadramento.getId()).get();
        // Disconnect from session so that the updates on updatedEnquadramento are not directly saved in db
        em.detach(updatedEnquadramento);
        updatedEnquadramento
            .nome(UPDATED_NOME)
            .limite(UPDATED_LIMITE);

        restEnquadramentoMockMvc.perform(put("/api/enquadramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnquadramento)))
            .andExpect(status().isOk());

        // Validate the Enquadramento in the database
        List<Enquadramento> enquadramentoList = enquadramentoRepository.findAll();
        assertThat(enquadramentoList).hasSize(databaseSizeBeforeUpdate);
        Enquadramento testEnquadramento = enquadramentoList.get(enquadramentoList.size() - 1);
        assertThat(testEnquadramento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEnquadramento.getLimite()).isEqualTo(UPDATED_LIMITE);

        // Validate the Enquadramento in Elasticsearch
        verify(mockEnquadramentoSearchRepository, times(1)).save(testEnquadramento);
    }

    @Test
    @Transactional
    public void updateNonExistingEnquadramento() throws Exception {
        int databaseSizeBeforeUpdate = enquadramentoRepository.findAll().size();

        // Create the Enquadramento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquadramentoMockMvc.perform(put("/api/enquadramentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enquadramento)))
            .andExpect(status().isBadRequest());

        // Validate the Enquadramento in the database
        List<Enquadramento> enquadramentoList = enquadramentoRepository.findAll();
        assertThat(enquadramentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Enquadramento in Elasticsearch
        verify(mockEnquadramentoSearchRepository, times(0)).save(enquadramento);
    }

    @Test
    @Transactional
    public void deleteEnquadramento() throws Exception {
        // Initialize the database
        enquadramentoRepository.saveAndFlush(enquadramento);

        int databaseSizeBeforeDelete = enquadramentoRepository.findAll().size();

        // Get the enquadramento
        restEnquadramentoMockMvc.perform(delete("/api/enquadramentos/{id}", enquadramento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enquadramento> enquadramentoList = enquadramentoRepository.findAll();
        assertThat(enquadramentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Enquadramento in Elasticsearch
        verify(mockEnquadramentoSearchRepository, times(1)).deleteById(enquadramento.getId());
    }

    @Test
    @Transactional
    public void searchEnquadramento() throws Exception {
        // Initialize the database
        enquadramentoRepository.saveAndFlush(enquadramento);
        when(mockEnquadramentoSearchRepository.search(queryStringQuery("id:" + enquadramento.getId())))
            .thenReturn(Collections.singletonList(enquadramento));
        // Search the enquadramento
        restEnquadramentoMockMvc.perform(get("/api/_search/enquadramentos?query=id:" + enquadramento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquadramento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].limite").value(hasItem(DEFAULT_LIMITE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enquadramento.class);
        Enquadramento enquadramento1 = new Enquadramento();
        enquadramento1.setId(1L);
        Enquadramento enquadramento2 = new Enquadramento();
        enquadramento2.setId(enquadramento1.getId());
        assertThat(enquadramento1).isEqualTo(enquadramento2);
        enquadramento2.setId(2L);
        assertThat(enquadramento1).isNotEqualTo(enquadramento2);
        enquadramento1.setId(null);
        assertThat(enquadramento1).isNotEqualTo(enquadramento2);
    }
}
