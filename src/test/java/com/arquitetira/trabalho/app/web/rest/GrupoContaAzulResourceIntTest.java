package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.GrupoContaAzul;
import com.arquitetira.trabalho.app.repository.GrupoContaAzulRepository;
import com.arquitetira.trabalho.app.repository.search.GrupoContaAzulSearchRepository;
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
 * Test class for the GrupoContaAzulResource REST controller.
 *
 * @see GrupoContaAzulResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class GrupoContaAzulResourceIntTest {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private GrupoContaAzulRepository grupoContaAzulRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.GrupoContaAzulSearchRepositoryMockConfiguration
     */
    @Autowired
    private GrupoContaAzulSearchRepository mockGrupoContaAzulSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrupoContaAzulMockMvc;

    private GrupoContaAzul grupoContaAzul;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupoContaAzulResource grupoContaAzulResource = new GrupoContaAzulResource(grupoContaAzulRepository, mockGrupoContaAzulSearchRepository);
        this.restGrupoContaAzulMockMvc = MockMvcBuilders.standaloneSetup(grupoContaAzulResource)
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
    public static GrupoContaAzul createEntity(EntityManager em) {
        GrupoContaAzul grupoContaAzul = new GrupoContaAzul()
            .estado(DEFAULT_ESTADO);
        return grupoContaAzul;
    }

    @Before
    public void initTest() {
        grupoContaAzul = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoContaAzul() throws Exception {
        int databaseSizeBeforeCreate = grupoContaAzulRepository.findAll().size();

        // Create the GrupoContaAzul
        restGrupoContaAzulMockMvc.perform(post("/api/grupo-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoContaAzul)))
            .andExpect(status().isCreated());

        // Validate the GrupoContaAzul in the database
        List<GrupoContaAzul> grupoContaAzulList = grupoContaAzulRepository.findAll();
        assertThat(grupoContaAzulList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoContaAzul testGrupoContaAzul = grupoContaAzulList.get(grupoContaAzulList.size() - 1);
        assertThat(testGrupoContaAzul.isEstado()).isEqualTo(DEFAULT_ESTADO);

        // Validate the GrupoContaAzul in Elasticsearch
        verify(mockGrupoContaAzulSearchRepository, times(1)).save(testGrupoContaAzul);
    }

    @Test
    @Transactional
    public void createGrupoContaAzulWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoContaAzulRepository.findAll().size();

        // Create the GrupoContaAzul with an existing ID
        grupoContaAzul.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoContaAzulMockMvc.perform(post("/api/grupo-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoContaAzul)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoContaAzul in the database
        List<GrupoContaAzul> grupoContaAzulList = grupoContaAzulRepository.findAll();
        assertThat(grupoContaAzulList).hasSize(databaseSizeBeforeCreate);

        // Validate the GrupoContaAzul in Elasticsearch
        verify(mockGrupoContaAzulSearchRepository, times(0)).save(grupoContaAzul);
    }

    @Test
    @Transactional
    public void getAllGrupoContaAzuls() throws Exception {
        // Initialize the database
        grupoContaAzulRepository.saveAndFlush(grupoContaAzul);

        // Get all the grupoContaAzulList
        restGrupoContaAzulMockMvc.perform(get("/api/grupo-conta-azuls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getGrupoContaAzul() throws Exception {
        // Initialize the database
        grupoContaAzulRepository.saveAndFlush(grupoContaAzul);

        // Get the grupoContaAzul
        restGrupoContaAzulMockMvc.perform(get("/api/grupo-conta-azuls/{id}", grupoContaAzul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupoContaAzul.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGrupoContaAzul() throws Exception {
        // Get the grupoContaAzul
        restGrupoContaAzulMockMvc.perform(get("/api/grupo-conta-azuls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoContaAzul() throws Exception {
        // Initialize the database
        grupoContaAzulRepository.saveAndFlush(grupoContaAzul);

        int databaseSizeBeforeUpdate = grupoContaAzulRepository.findAll().size();

        // Update the grupoContaAzul
        GrupoContaAzul updatedGrupoContaAzul = grupoContaAzulRepository.findById(grupoContaAzul.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoContaAzul are not directly saved in db
        em.detach(updatedGrupoContaAzul);
        updatedGrupoContaAzul
            .estado(UPDATED_ESTADO);

        restGrupoContaAzulMockMvc.perform(put("/api/grupo-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupoContaAzul)))
            .andExpect(status().isOk());

        // Validate the GrupoContaAzul in the database
        List<GrupoContaAzul> grupoContaAzulList = grupoContaAzulRepository.findAll();
        assertThat(grupoContaAzulList).hasSize(databaseSizeBeforeUpdate);
        GrupoContaAzul testGrupoContaAzul = grupoContaAzulList.get(grupoContaAzulList.size() - 1);
        assertThat(testGrupoContaAzul.isEstado()).isEqualTo(UPDATED_ESTADO);

        // Validate the GrupoContaAzul in Elasticsearch
        verify(mockGrupoContaAzulSearchRepository, times(1)).save(testGrupoContaAzul);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoContaAzul() throws Exception {
        int databaseSizeBeforeUpdate = grupoContaAzulRepository.findAll().size();

        // Create the GrupoContaAzul

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoContaAzulMockMvc.perform(put("/api/grupo-conta-azuls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoContaAzul)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoContaAzul in the database
        List<GrupoContaAzul> grupoContaAzulList = grupoContaAzulRepository.findAll();
        assertThat(grupoContaAzulList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GrupoContaAzul in Elasticsearch
        verify(mockGrupoContaAzulSearchRepository, times(0)).save(grupoContaAzul);
    }

    @Test
    @Transactional
    public void deleteGrupoContaAzul() throws Exception {
        // Initialize the database
        grupoContaAzulRepository.saveAndFlush(grupoContaAzul);

        int databaseSizeBeforeDelete = grupoContaAzulRepository.findAll().size();

        // Get the grupoContaAzul
        restGrupoContaAzulMockMvc.perform(delete("/api/grupo-conta-azuls/{id}", grupoContaAzul.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GrupoContaAzul> grupoContaAzulList = grupoContaAzulRepository.findAll();
        assertThat(grupoContaAzulList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GrupoContaAzul in Elasticsearch
        verify(mockGrupoContaAzulSearchRepository, times(1)).deleteById(grupoContaAzul.getId());
    }

    @Test
    @Transactional
    public void searchGrupoContaAzul() throws Exception {
        // Initialize the database
        grupoContaAzulRepository.saveAndFlush(grupoContaAzul);
        when(mockGrupoContaAzulSearchRepository.search(queryStringQuery("id:" + grupoContaAzul.getId())))
            .thenReturn(Collections.singletonList(grupoContaAzul));
        // Search the grupoContaAzul
        restGrupoContaAzulMockMvc.perform(get("/api/_search/grupo-conta-azuls?query=id:" + grupoContaAzul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoContaAzul.class);
        GrupoContaAzul grupoContaAzul1 = new GrupoContaAzul();
        grupoContaAzul1.setId(1L);
        GrupoContaAzul grupoContaAzul2 = new GrupoContaAzul();
        grupoContaAzul2.setId(grupoContaAzul1.getId());
        assertThat(grupoContaAzul1).isEqualTo(grupoContaAzul2);
        grupoContaAzul2.setId(2L);
        assertThat(grupoContaAzul1).isNotEqualTo(grupoContaAzul2);
        grupoContaAzul1.setId(null);
        assertThat(grupoContaAzul1).isNotEqualTo(grupoContaAzul2);
    }
}
