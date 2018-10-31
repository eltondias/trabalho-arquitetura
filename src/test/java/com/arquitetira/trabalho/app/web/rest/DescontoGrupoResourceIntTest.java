package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.DescontoGrupo;
import com.arquitetira.trabalho.app.repository.DescontoGrupoRepository;
import com.arquitetira.trabalho.app.repository.search.DescontoGrupoSearchRepository;
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
 * Test class for the DescontoGrupoResource REST controller.
 *
 * @see DescontoGrupoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class DescontoGrupoResourceIntTest {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private DescontoGrupoRepository descontoGrupoRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.DescontoGrupoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DescontoGrupoSearchRepository mockDescontoGrupoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDescontoGrupoMockMvc;

    private DescontoGrupo descontoGrupo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DescontoGrupoResource descontoGrupoResource = new DescontoGrupoResource(descontoGrupoRepository, mockDescontoGrupoSearchRepository);
        this.restDescontoGrupoMockMvc = MockMvcBuilders.standaloneSetup(descontoGrupoResource)
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
    public static DescontoGrupo createEntity(EntityManager em) {
        DescontoGrupo descontoGrupo = new DescontoGrupo()
            .estado(DEFAULT_ESTADO);
        return descontoGrupo;
    }

    @Before
    public void initTest() {
        descontoGrupo = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescontoGrupo() throws Exception {
        int databaseSizeBeforeCreate = descontoGrupoRepository.findAll().size();

        // Create the DescontoGrupo
        restDescontoGrupoMockMvc.perform(post("/api/desconto-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descontoGrupo)))
            .andExpect(status().isCreated());

        // Validate the DescontoGrupo in the database
        List<DescontoGrupo> descontoGrupoList = descontoGrupoRepository.findAll();
        assertThat(descontoGrupoList).hasSize(databaseSizeBeforeCreate + 1);
        DescontoGrupo testDescontoGrupo = descontoGrupoList.get(descontoGrupoList.size() - 1);
        assertThat(testDescontoGrupo.isEstado()).isEqualTo(DEFAULT_ESTADO);

        // Validate the DescontoGrupo in Elasticsearch
        verify(mockDescontoGrupoSearchRepository, times(1)).save(testDescontoGrupo);
    }

    @Test
    @Transactional
    public void createDescontoGrupoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descontoGrupoRepository.findAll().size();

        // Create the DescontoGrupo with an existing ID
        descontoGrupo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescontoGrupoMockMvc.perform(post("/api/desconto-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descontoGrupo)))
            .andExpect(status().isBadRequest());

        // Validate the DescontoGrupo in the database
        List<DescontoGrupo> descontoGrupoList = descontoGrupoRepository.findAll();
        assertThat(descontoGrupoList).hasSize(databaseSizeBeforeCreate);

        // Validate the DescontoGrupo in Elasticsearch
        verify(mockDescontoGrupoSearchRepository, times(0)).save(descontoGrupo);
    }

    @Test
    @Transactional
    public void getAllDescontoGrupos() throws Exception {
        // Initialize the database
        descontoGrupoRepository.saveAndFlush(descontoGrupo);

        // Get all the descontoGrupoList
        restDescontoGrupoMockMvc.perform(get("/api/desconto-grupos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoGrupo.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDescontoGrupo() throws Exception {
        // Initialize the database
        descontoGrupoRepository.saveAndFlush(descontoGrupo);

        // Get the descontoGrupo
        restDescontoGrupoMockMvc.perform(get("/api/desconto-grupos/{id}", descontoGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(descontoGrupo.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDescontoGrupo() throws Exception {
        // Get the descontoGrupo
        restDescontoGrupoMockMvc.perform(get("/api/desconto-grupos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescontoGrupo() throws Exception {
        // Initialize the database
        descontoGrupoRepository.saveAndFlush(descontoGrupo);

        int databaseSizeBeforeUpdate = descontoGrupoRepository.findAll().size();

        // Update the descontoGrupo
        DescontoGrupo updatedDescontoGrupo = descontoGrupoRepository.findById(descontoGrupo.getId()).get();
        // Disconnect from session so that the updates on updatedDescontoGrupo are not directly saved in db
        em.detach(updatedDescontoGrupo);
        updatedDescontoGrupo
            .estado(UPDATED_ESTADO);

        restDescontoGrupoMockMvc.perform(put("/api/desconto-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDescontoGrupo)))
            .andExpect(status().isOk());

        // Validate the DescontoGrupo in the database
        List<DescontoGrupo> descontoGrupoList = descontoGrupoRepository.findAll();
        assertThat(descontoGrupoList).hasSize(databaseSizeBeforeUpdate);
        DescontoGrupo testDescontoGrupo = descontoGrupoList.get(descontoGrupoList.size() - 1);
        assertThat(testDescontoGrupo.isEstado()).isEqualTo(UPDATED_ESTADO);

        // Validate the DescontoGrupo in Elasticsearch
        verify(mockDescontoGrupoSearchRepository, times(1)).save(testDescontoGrupo);
    }

    @Test
    @Transactional
    public void updateNonExistingDescontoGrupo() throws Exception {
        int databaseSizeBeforeUpdate = descontoGrupoRepository.findAll().size();

        // Create the DescontoGrupo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoGrupoMockMvc.perform(put("/api/desconto-grupos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descontoGrupo)))
            .andExpect(status().isBadRequest());

        // Validate the DescontoGrupo in the database
        List<DescontoGrupo> descontoGrupoList = descontoGrupoRepository.findAll();
        assertThat(descontoGrupoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DescontoGrupo in Elasticsearch
        verify(mockDescontoGrupoSearchRepository, times(0)).save(descontoGrupo);
    }

    @Test
    @Transactional
    public void deleteDescontoGrupo() throws Exception {
        // Initialize the database
        descontoGrupoRepository.saveAndFlush(descontoGrupo);

        int databaseSizeBeforeDelete = descontoGrupoRepository.findAll().size();

        // Get the descontoGrupo
        restDescontoGrupoMockMvc.perform(delete("/api/desconto-grupos/{id}", descontoGrupo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DescontoGrupo> descontoGrupoList = descontoGrupoRepository.findAll();
        assertThat(descontoGrupoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DescontoGrupo in Elasticsearch
        verify(mockDescontoGrupoSearchRepository, times(1)).deleteById(descontoGrupo.getId());
    }

    @Test
    @Transactional
    public void searchDescontoGrupo() throws Exception {
        // Initialize the database
        descontoGrupoRepository.saveAndFlush(descontoGrupo);
        when(mockDescontoGrupoSearchRepository.search(queryStringQuery("id:" + descontoGrupo.getId())))
            .thenReturn(Collections.singletonList(descontoGrupo));
        // Search the descontoGrupo
        restDescontoGrupoMockMvc.perform(get("/api/_search/desconto-grupos?query=id:" + descontoGrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoGrupo.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoGrupo.class);
        DescontoGrupo descontoGrupo1 = new DescontoGrupo();
        descontoGrupo1.setId(1L);
        DescontoGrupo descontoGrupo2 = new DescontoGrupo();
        descontoGrupo2.setId(descontoGrupo1.getId());
        assertThat(descontoGrupo1).isEqualTo(descontoGrupo2);
        descontoGrupo2.setId(2L);
        assertThat(descontoGrupo1).isNotEqualTo(descontoGrupo2);
        descontoGrupo1.setId(null);
        assertThat(descontoGrupo1).isNotEqualTo(descontoGrupo2);
    }
}
