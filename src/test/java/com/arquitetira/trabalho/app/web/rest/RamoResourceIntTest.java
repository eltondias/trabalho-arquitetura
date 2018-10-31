package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.Ramo;
import com.arquitetira.trabalho.app.repository.RamoRepository;
import com.arquitetira.trabalho.app.repository.search.RamoSearchRepository;
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
 * Test class for the RamoResource REST controller.
 *
 * @see RamoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class RamoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private RamoRepository ramoRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.RamoSearchRepositoryMockConfiguration
     */
    @Autowired
    private RamoSearchRepository mockRamoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRamoMockMvc;

    private Ramo ramo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RamoResource ramoResource = new RamoResource(ramoRepository, mockRamoSearchRepository);
        this.restRamoMockMvc = MockMvcBuilders.standaloneSetup(ramoResource)
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
    public static Ramo createEntity(EntityManager em) {
        Ramo ramo = new Ramo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return ramo;
    }

    @Before
    public void initTest() {
        ramo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRamo() throws Exception {
        int databaseSizeBeforeCreate = ramoRepository.findAll().size();

        // Create the Ramo
        restRamoMockMvc.perform(post("/api/ramos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ramo)))
            .andExpect(status().isCreated());

        // Validate the Ramo in the database
        List<Ramo> ramoList = ramoRepository.findAll();
        assertThat(ramoList).hasSize(databaseSizeBeforeCreate + 1);
        Ramo testRamo = ramoList.get(ramoList.size() - 1);
        assertThat(testRamo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testRamo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the Ramo in Elasticsearch
        verify(mockRamoSearchRepository, times(1)).save(testRamo);
    }

    @Test
    @Transactional
    public void createRamoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ramoRepository.findAll().size();

        // Create the Ramo with an existing ID
        ramo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRamoMockMvc.perform(post("/api/ramos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ramo)))
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        List<Ramo> ramoList = ramoRepository.findAll();
        assertThat(ramoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ramo in Elasticsearch
        verify(mockRamoSearchRepository, times(0)).save(ramo);
    }

    @Test
    @Transactional
    public void getAllRamos() throws Exception {
        // Initialize the database
        ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList
        restRamoMockMvc.perform(get("/api/ramos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ramo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getRamo() throws Exception {
        // Initialize the database
        ramoRepository.saveAndFlush(ramo);

        // Get the ramo
        restRamoMockMvc.perform(get("/api/ramos/{id}", ramo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ramo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRamo() throws Exception {
        // Get the ramo
        restRamoMockMvc.perform(get("/api/ramos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRamo() throws Exception {
        // Initialize the database
        ramoRepository.saveAndFlush(ramo);

        int databaseSizeBeforeUpdate = ramoRepository.findAll().size();

        // Update the ramo
        Ramo updatedRamo = ramoRepository.findById(ramo.getId()).get();
        // Disconnect from session so that the updates on updatedRamo are not directly saved in db
        em.detach(updatedRamo);
        updatedRamo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);

        restRamoMockMvc.perform(put("/api/ramos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRamo)))
            .andExpect(status().isOk());

        // Validate the Ramo in the database
        List<Ramo> ramoList = ramoRepository.findAll();
        assertThat(ramoList).hasSize(databaseSizeBeforeUpdate);
        Ramo testRamo = ramoList.get(ramoList.size() - 1);
        assertThat(testRamo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testRamo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the Ramo in Elasticsearch
        verify(mockRamoSearchRepository, times(1)).save(testRamo);
    }

    @Test
    @Transactional
    public void updateNonExistingRamo() throws Exception {
        int databaseSizeBeforeUpdate = ramoRepository.findAll().size();

        // Create the Ramo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRamoMockMvc.perform(put("/api/ramos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ramo)))
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        List<Ramo> ramoList = ramoRepository.findAll();
        assertThat(ramoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ramo in Elasticsearch
        verify(mockRamoSearchRepository, times(0)).save(ramo);
    }

    @Test
    @Transactional
    public void deleteRamo() throws Exception {
        // Initialize the database
        ramoRepository.saveAndFlush(ramo);

        int databaseSizeBeforeDelete = ramoRepository.findAll().size();

        // Get the ramo
        restRamoMockMvc.perform(delete("/api/ramos/{id}", ramo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ramo> ramoList = ramoRepository.findAll();
        assertThat(ramoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ramo in Elasticsearch
        verify(mockRamoSearchRepository, times(1)).deleteById(ramo.getId());
    }

    @Test
    @Transactional
    public void searchRamo() throws Exception {
        // Initialize the database
        ramoRepository.saveAndFlush(ramo);
        when(mockRamoSearchRepository.search(queryStringQuery("id:" + ramo.getId())))
            .thenReturn(Collections.singletonList(ramo));
        // Search the ramo
        restRamoMockMvc.perform(get("/api/_search/ramos?query=id:" + ramo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ramo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ramo.class);
        Ramo ramo1 = new Ramo();
        ramo1.setId(1L);
        Ramo ramo2 = new Ramo();
        ramo2.setId(ramo1.getId());
        assertThat(ramo1).isEqualTo(ramo2);
        ramo2.setId(2L);
        assertThat(ramo1).isNotEqualTo(ramo2);
        ramo1.setId(null);
        assertThat(ramo1).isNotEqualTo(ramo2);
    }
}
