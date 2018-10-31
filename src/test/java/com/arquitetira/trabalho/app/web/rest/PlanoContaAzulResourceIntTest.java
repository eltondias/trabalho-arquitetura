package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.PlanoContaAzul;
import com.arquitetira.trabalho.app.repository.PlanoContaAzulRepository;
import com.arquitetira.trabalho.app.repository.search.PlanoContaAzulSearchRepository;
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
 * Test class for the PlanoContaAzulResource REST controller.
 *
 * @see PlanoContaAzulResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class PlanoContaAzulResourceIntTest {

    private static final Long DEFAULT_PLANO = 1L;
    private static final Long UPDATED_PLANO = 2L;

    @Autowired
    private PlanoContaAzulRepository planoContaAzulRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.PlanoContaAzulSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlanoContaAzulSearchRepository mockPlanoContaAzulSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanoContaAzulMockMvc;

    private PlanoContaAzul planoContaAzul;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanoContaAzulResource planoContaAzulResource = new PlanoContaAzulResource(planoContaAzulRepository, mockPlanoContaAzulSearchRepository);
        this.restPlanoContaAzulMockMvc = MockMvcBuilders.standaloneSetup(planoContaAzulResource)
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
    public static PlanoContaAzul createEntity(EntityManager em) {
        PlanoContaAzul planoContaAzul = new PlanoContaAzul()
            .plano(DEFAULT_PLANO);
        return planoContaAzul;
    }

    @Before
    public void initTest() {
        planoContaAzul = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanoContaAzul() throws Exception {
        int databaseSizeBeforeCreate = planoContaAzulRepository.findAll().size();

        // Create the PlanoContaAzul
        restPlanoContaAzulMockMvc.perform(post("/api/plano-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoContaAzul)))
            .andExpect(status().isCreated());

        // Validate the PlanoContaAzul in the database
        List<PlanoContaAzul> planoContaAzulList = planoContaAzulRepository.findAll();
        assertThat(planoContaAzulList).hasSize(databaseSizeBeforeCreate + 1);
        PlanoContaAzul testPlanoContaAzul = planoContaAzulList.get(planoContaAzulList.size() - 1);
        assertThat(testPlanoContaAzul.getPlano()).isEqualTo(DEFAULT_PLANO);

        // Validate the PlanoContaAzul in Elasticsearch
        verify(mockPlanoContaAzulSearchRepository, times(1)).save(testPlanoContaAzul);
    }

    @Test
    @Transactional
    public void createPlanoContaAzulWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planoContaAzulRepository.findAll().size();

        // Create the PlanoContaAzul with an existing ID
        planoContaAzul.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoContaAzulMockMvc.perform(post("/api/plano-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoContaAzul)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        List<PlanoContaAzul> planoContaAzulList = planoContaAzulRepository.findAll();
        assertThat(planoContaAzulList).hasSize(databaseSizeBeforeCreate);

        // Validate the PlanoContaAzul in Elasticsearch
        verify(mockPlanoContaAzulSearchRepository, times(0)).save(planoContaAzul);
    }

    @Test
    @Transactional
    public void getAllPlanoContaAzuls() throws Exception {
        // Initialize the database
        planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList
        restPlanoContaAzulMockMvc.perform(get("/api/plano-conta-azuls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].plano").value(hasItem(DEFAULT_PLANO.intValue())));
    }
    
    @Test
    @Transactional
    public void getPlanoContaAzul() throws Exception {
        // Initialize the database
        planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get the planoContaAzul
        restPlanoContaAzulMockMvc.perform(get("/api/plano-conta-azuls/{id}", planoContaAzul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planoContaAzul.getId().intValue()))
            .andExpect(jsonPath("$.plano").value(DEFAULT_PLANO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanoContaAzul() throws Exception {
        // Get the planoContaAzul
        restPlanoContaAzulMockMvc.perform(get("/api/plano-conta-azuls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanoContaAzul() throws Exception {
        // Initialize the database
        planoContaAzulRepository.saveAndFlush(planoContaAzul);

        int databaseSizeBeforeUpdate = planoContaAzulRepository.findAll().size();

        // Update the planoContaAzul
        PlanoContaAzul updatedPlanoContaAzul = planoContaAzulRepository.findById(planoContaAzul.getId()).get();
        // Disconnect from session so that the updates on updatedPlanoContaAzul are not directly saved in db
        em.detach(updatedPlanoContaAzul);
        updatedPlanoContaAzul
            .plano(UPDATED_PLANO);

        restPlanoContaAzulMockMvc.perform(put("/api/plano-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanoContaAzul)))
            .andExpect(status().isOk());

        // Validate the PlanoContaAzul in the database
        List<PlanoContaAzul> planoContaAzulList = planoContaAzulRepository.findAll();
        assertThat(planoContaAzulList).hasSize(databaseSizeBeforeUpdate);
        PlanoContaAzul testPlanoContaAzul = planoContaAzulList.get(planoContaAzulList.size() - 1);
        assertThat(testPlanoContaAzul.getPlano()).isEqualTo(UPDATED_PLANO);

        // Validate the PlanoContaAzul in Elasticsearch
        verify(mockPlanoContaAzulSearchRepository, times(1)).save(testPlanoContaAzul);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanoContaAzul() throws Exception {
        int databaseSizeBeforeUpdate = planoContaAzulRepository.findAll().size();

        // Create the PlanoContaAzul

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc.perform(put("/api/plano-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planoContaAzul)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        List<PlanoContaAzul> planoContaAzulList = planoContaAzulRepository.findAll();
        assertThat(planoContaAzulList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PlanoContaAzul in Elasticsearch
        verify(mockPlanoContaAzulSearchRepository, times(0)).save(planoContaAzul);
    }

    @Test
    @Transactional
    public void deletePlanoContaAzul() throws Exception {
        // Initialize the database
        planoContaAzulRepository.saveAndFlush(planoContaAzul);

        int databaseSizeBeforeDelete = planoContaAzulRepository.findAll().size();

        // Get the planoContaAzul
        restPlanoContaAzulMockMvc.perform(delete("/api/plano-conta-azuls/{id}", planoContaAzul.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanoContaAzul> planoContaAzulList = planoContaAzulRepository.findAll();
        assertThat(planoContaAzulList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PlanoContaAzul in Elasticsearch
        verify(mockPlanoContaAzulSearchRepository, times(1)).deleteById(planoContaAzul.getId());
    }

    @Test
    @Transactional
    public void searchPlanoContaAzul() throws Exception {
        // Initialize the database
        planoContaAzulRepository.saveAndFlush(planoContaAzul);
        when(mockPlanoContaAzulSearchRepository.search(queryStringQuery("id:" + planoContaAzul.getId())))
            .thenReturn(Collections.singletonList(planoContaAzul));
        // Search the planoContaAzul
        restPlanoContaAzulMockMvc.perform(get("/api/_search/plano-conta-azuls?query=id:" + planoContaAzul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].plano").value(hasItem(DEFAULT_PLANO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoContaAzul.class);
        PlanoContaAzul planoContaAzul1 = new PlanoContaAzul();
        planoContaAzul1.setId(1L);
        PlanoContaAzul planoContaAzul2 = new PlanoContaAzul();
        planoContaAzul2.setId(planoContaAzul1.getId());
        assertThat(planoContaAzul1).isEqualTo(planoContaAzul2);
        planoContaAzul2.setId(2L);
        assertThat(planoContaAzul1).isNotEqualTo(planoContaAzul2);
        planoContaAzul1.setId(null);
        assertThat(planoContaAzul1).isNotEqualTo(planoContaAzul2);
    }
}
