package com.arquitetira.trabalho.app.web.rest;

import com.arquitetira.trabalho.app.TrabalhoarquiteturaApp;

import com.arquitetira.trabalho.app.domain.DescontoPlanoContabil;
import com.arquitetira.trabalho.app.repository.DescontoPlanoContabilRepository;
import com.arquitetira.trabalho.app.repository.search.DescontoPlanoContabilSearchRepository;
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
 * Test class for the DescontoPlanoContabilResource REST controller.
 *
 * @see DescontoPlanoContabilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoarquiteturaApp.class)
public class DescontoPlanoContabilResourceIntTest {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    /**
     * This repository is mocked in the com.arquitetira.trabalho.app.repository.search test package.
     *
     * @see com.arquitetira.trabalho.app.repository.search.DescontoPlanoContabilSearchRepositoryMockConfiguration
     */
    @Autowired
    private DescontoPlanoContabilSearchRepository mockDescontoPlanoContabilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDescontoPlanoContabilMockMvc;

    private DescontoPlanoContabil descontoPlanoContabil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DescontoPlanoContabilResource descontoPlanoContabilResource = new DescontoPlanoContabilResource(descontoPlanoContabilRepository, mockDescontoPlanoContabilSearchRepository);
        this.restDescontoPlanoContabilMockMvc = MockMvcBuilders.standaloneSetup(descontoPlanoContabilResource)
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
    public static DescontoPlanoContabil createEntity(EntityManager em) {
        DescontoPlanoContabil descontoPlanoContabil = new DescontoPlanoContabil()
            .estado(DEFAULT_ESTADO);
        return descontoPlanoContabil;
    }

    @Before
    public void initTest() {
        descontoPlanoContabil = createEntity(em);
    }

    @Test
    @Transactional
    public void createDescontoPlanoContabil() throws Exception {
        int databaseSizeBeforeCreate = descontoPlanoContabilRepository.findAll().size();

        // Create the DescontoPlanoContabil
        restDescontoPlanoContabilMockMvc.perform(post("/api/desconto-plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descontoPlanoContabil)))
            .andExpect(status().isCreated());

        // Validate the DescontoPlanoContabil in the database
        List<DescontoPlanoContabil> descontoPlanoContabilList = descontoPlanoContabilRepository.findAll();
        assertThat(descontoPlanoContabilList).hasSize(databaseSizeBeforeCreate + 1);
        DescontoPlanoContabil testDescontoPlanoContabil = descontoPlanoContabilList.get(descontoPlanoContabilList.size() - 1);
        assertThat(testDescontoPlanoContabil.isEstado()).isEqualTo(DEFAULT_ESTADO);

        // Validate the DescontoPlanoContabil in Elasticsearch
        verify(mockDescontoPlanoContabilSearchRepository, times(1)).save(testDescontoPlanoContabil);
    }

    @Test
    @Transactional
    public void createDescontoPlanoContabilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = descontoPlanoContabilRepository.findAll().size();

        // Create the DescontoPlanoContabil with an existing ID
        descontoPlanoContabil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescontoPlanoContabilMockMvc.perform(post("/api/desconto-plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descontoPlanoContabil)))
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        List<DescontoPlanoContabil> descontoPlanoContabilList = descontoPlanoContabilRepository.findAll();
        assertThat(descontoPlanoContabilList).hasSize(databaseSizeBeforeCreate);

        // Validate the DescontoPlanoContabil in Elasticsearch
        verify(mockDescontoPlanoContabilSearchRepository, times(0)).save(descontoPlanoContabil);
    }

    @Test
    @Transactional
    public void getAllDescontoPlanoContabils() throws Exception {
        // Initialize the database
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList
        restDescontoPlanoContabilMockMvc.perform(get("/api/desconto-plano-contabils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPlanoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDescontoPlanoContabil() throws Exception {
        // Initialize the database
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc.perform(get("/api/desconto-plano-contabils/{id}", descontoPlanoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(descontoPlanoContabil.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDescontoPlanoContabil() throws Exception {
        // Get the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc.perform(get("/api/desconto-plano-contabils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDescontoPlanoContabil() throws Exception {
        // Initialize the database
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        int databaseSizeBeforeUpdate = descontoPlanoContabilRepository.findAll().size();

        // Update the descontoPlanoContabil
        DescontoPlanoContabil updatedDescontoPlanoContabil = descontoPlanoContabilRepository.findById(descontoPlanoContabil.getId()).get();
        // Disconnect from session so that the updates on updatedDescontoPlanoContabil are not directly saved in db
        em.detach(updatedDescontoPlanoContabil);
        updatedDescontoPlanoContabil
            .estado(UPDATED_ESTADO);

        restDescontoPlanoContabilMockMvc.perform(put("/api/desconto-plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDescontoPlanoContabil)))
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContabil in the database
        List<DescontoPlanoContabil> descontoPlanoContabilList = descontoPlanoContabilRepository.findAll();
        assertThat(descontoPlanoContabilList).hasSize(databaseSizeBeforeUpdate);
        DescontoPlanoContabil testDescontoPlanoContabil = descontoPlanoContabilList.get(descontoPlanoContabilList.size() - 1);
        assertThat(testDescontoPlanoContabil.isEstado()).isEqualTo(UPDATED_ESTADO);

        // Validate the DescontoPlanoContabil in Elasticsearch
        verify(mockDescontoPlanoContabilSearchRepository, times(1)).save(testDescontoPlanoContabil);
    }

    @Test
    @Transactional
    public void updateNonExistingDescontoPlanoContabil() throws Exception {
        int databaseSizeBeforeUpdate = descontoPlanoContabilRepository.findAll().size();

        // Create the DescontoPlanoContabil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc.perform(put("/api/desconto-plano-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(descontoPlanoContabil)))
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        List<DescontoPlanoContabil> descontoPlanoContabilList = descontoPlanoContabilRepository.findAll();
        assertThat(descontoPlanoContabilList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DescontoPlanoContabil in Elasticsearch
        verify(mockDescontoPlanoContabilSearchRepository, times(0)).save(descontoPlanoContabil);
    }

    @Test
    @Transactional
    public void deleteDescontoPlanoContabil() throws Exception {
        // Initialize the database
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        int databaseSizeBeforeDelete = descontoPlanoContabilRepository.findAll().size();

        // Get the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc.perform(delete("/api/desconto-plano-contabils/{id}", descontoPlanoContabil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DescontoPlanoContabil> descontoPlanoContabilList = descontoPlanoContabilRepository.findAll();
        assertThat(descontoPlanoContabilList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DescontoPlanoContabil in Elasticsearch
        verify(mockDescontoPlanoContabilSearchRepository, times(1)).deleteById(descontoPlanoContabil.getId());
    }

    @Test
    @Transactional
    public void searchDescontoPlanoContabil() throws Exception {
        // Initialize the database
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);
        when(mockDescontoPlanoContabilSearchRepository.search(queryStringQuery("id:" + descontoPlanoContabil.getId())))
            .thenReturn(Collections.singletonList(descontoPlanoContabil));
        // Search the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc.perform(get("/api/_search/desconto-plano-contabils?query=id:" + descontoPlanoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPlanoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescontoPlanoContabil.class);
        DescontoPlanoContabil descontoPlanoContabil1 = new DescontoPlanoContabil();
        descontoPlanoContabil1.setId(1L);
        DescontoPlanoContabil descontoPlanoContabil2 = new DescontoPlanoContabil();
        descontoPlanoContabil2.setId(descontoPlanoContabil1.getId());
        assertThat(descontoPlanoContabil1).isEqualTo(descontoPlanoContabil2);
        descontoPlanoContabil2.setId(2L);
        assertThat(descontoPlanoContabil1).isNotEqualTo(descontoPlanoContabil2);
        descontoPlanoContabil1.setId(null);
        assertThat(descontoPlanoContabil1).isNotEqualTo(descontoPlanoContabil2);
    }
}
