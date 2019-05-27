package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Devoir;
import com.lms.repository.DevoirRepository;
import com.lms.service.DevoirService;
import com.lms.service.dto.DevoirDTO;
import com.lms.service.mapper.DevoirMapper;
import com.lms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DevoirResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class DevoirResourceIT {

    private static final String DEFAULT_TITRE_DEVOIR = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_DEVOIR = "BBBBBBBBBB";

    private static final String DEFAULT_CHEMIN_DEVOIR = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN_DEVOIR = "BBBBBBBBBB";

    @Autowired
    private DevoirRepository devoirRepository;

    @Autowired
    private DevoirMapper devoirMapper;

    @Autowired
    private DevoirService devoirService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDevoirMockMvc;

    private Devoir devoir;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevoirResource devoirResource = new DevoirResource(devoirService);
        this.restDevoirMockMvc = MockMvcBuilders.standaloneSetup(devoirResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devoir createEntity(EntityManager em) {
        Devoir devoir = new Devoir()
            .titreDevoir(DEFAULT_TITRE_DEVOIR)
            .cheminDevoir(DEFAULT_CHEMIN_DEVOIR);
        return devoir;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devoir createUpdatedEntity(EntityManager em) {
        Devoir devoir = new Devoir()
            .titreDevoir(UPDATED_TITRE_DEVOIR)
            .cheminDevoir(UPDATED_CHEMIN_DEVOIR);
        return devoir;
    }

    @BeforeEach
    public void initTest() {
        devoir = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevoir() throws Exception {
        int databaseSizeBeforeCreate = devoirRepository.findAll().size();

        // Create the Devoir
        DevoirDTO devoirDTO = devoirMapper.toDto(devoir);
        restDevoirMockMvc.perform(post("/api/devoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devoirDTO)))
            .andExpect(status().isCreated());

        // Validate the Devoir in the database
        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeCreate + 1);
        Devoir testDevoir = devoirList.get(devoirList.size() - 1);
        assertThat(testDevoir.getTitreDevoir()).isEqualTo(DEFAULT_TITRE_DEVOIR);
        assertThat(testDevoir.getCheminDevoir()).isEqualTo(DEFAULT_CHEMIN_DEVOIR);
    }

    @Test
    @Transactional
    public void createDevoirWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devoirRepository.findAll().size();

        // Create the Devoir with an existing ID
        devoir.setId(1L);
        DevoirDTO devoirDTO = devoirMapper.toDto(devoir);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevoirMockMvc.perform(post("/api/devoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devoirDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Devoir in the database
        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitreDevoirIsRequired() throws Exception {
        int databaseSizeBeforeTest = devoirRepository.findAll().size();
        // set the field null
        devoir.setTitreDevoir(null);

        // Create the Devoir, which fails.
        DevoirDTO devoirDTO = devoirMapper.toDto(devoir);

        restDevoirMockMvc.perform(post("/api/devoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devoirDTO)))
            .andExpect(status().isBadRequest());

        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminDevoirIsRequired() throws Exception {
        int databaseSizeBeforeTest = devoirRepository.findAll().size();
        // set the field null
        devoir.setCheminDevoir(null);

        // Create the Devoir, which fails.
        DevoirDTO devoirDTO = devoirMapper.toDto(devoir);

        restDevoirMockMvc.perform(post("/api/devoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devoirDTO)))
            .andExpect(status().isBadRequest());

        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevoirs() throws Exception {
        // Initialize the database
        devoirRepository.saveAndFlush(devoir);

        // Get all the devoirList
        restDevoirMockMvc.perform(get("/api/devoirs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devoir.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreDevoir").value(hasItem(DEFAULT_TITRE_DEVOIR.toString())))
            .andExpect(jsonPath("$.[*].cheminDevoir").value(hasItem(DEFAULT_CHEMIN_DEVOIR.toString())));
    }
    
    @Test
    @Transactional
    public void getDevoir() throws Exception {
        // Initialize the database
        devoirRepository.saveAndFlush(devoir);

        // Get the devoir
        restDevoirMockMvc.perform(get("/api/devoirs/{id}", devoir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devoir.getId().intValue()))
            .andExpect(jsonPath("$.titreDevoir").value(DEFAULT_TITRE_DEVOIR.toString()))
            .andExpect(jsonPath("$.cheminDevoir").value(DEFAULT_CHEMIN_DEVOIR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDevoir() throws Exception {
        // Get the devoir
        restDevoirMockMvc.perform(get("/api/devoirs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevoir() throws Exception {
        // Initialize the database
        devoirRepository.saveAndFlush(devoir);

        int databaseSizeBeforeUpdate = devoirRepository.findAll().size();

        // Update the devoir
        Devoir updatedDevoir = devoirRepository.findById(devoir.getId()).get();
        // Disconnect from session so that the updates on updatedDevoir are not directly saved in db
        em.detach(updatedDevoir);
        updatedDevoir
            .titreDevoir(UPDATED_TITRE_DEVOIR)
            .cheminDevoir(UPDATED_CHEMIN_DEVOIR);
        DevoirDTO devoirDTO = devoirMapper.toDto(updatedDevoir);

        restDevoirMockMvc.perform(put("/api/devoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devoirDTO)))
            .andExpect(status().isOk());

        // Validate the Devoir in the database
        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeUpdate);
        Devoir testDevoir = devoirList.get(devoirList.size() - 1);
        assertThat(testDevoir.getTitreDevoir()).isEqualTo(UPDATED_TITRE_DEVOIR);
        assertThat(testDevoir.getCheminDevoir()).isEqualTo(UPDATED_CHEMIN_DEVOIR);
    }

    @Test
    @Transactional
    public void updateNonExistingDevoir() throws Exception {
        int databaseSizeBeforeUpdate = devoirRepository.findAll().size();

        // Create the Devoir
        DevoirDTO devoirDTO = devoirMapper.toDto(devoir);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevoirMockMvc.perform(put("/api/devoirs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devoirDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Devoir in the database
        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevoir() throws Exception {
        // Initialize the database
        devoirRepository.saveAndFlush(devoir);

        int databaseSizeBeforeDelete = devoirRepository.findAll().size();

        // Delete the devoir
        restDevoirMockMvc.perform(delete("/api/devoirs/{id}", devoir.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Devoir> devoirList = devoirRepository.findAll();
        assertThat(devoirList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devoir.class);
        Devoir devoir1 = new Devoir();
        devoir1.setId(1L);
        Devoir devoir2 = new Devoir();
        devoir2.setId(devoir1.getId());
        assertThat(devoir1).isEqualTo(devoir2);
        devoir2.setId(2L);
        assertThat(devoir1).isNotEqualTo(devoir2);
        devoir1.setId(null);
        assertThat(devoir1).isNotEqualTo(devoir2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevoirDTO.class);
        DevoirDTO devoirDTO1 = new DevoirDTO();
        devoirDTO1.setId(1L);
        DevoirDTO devoirDTO2 = new DevoirDTO();
        assertThat(devoirDTO1).isNotEqualTo(devoirDTO2);
        devoirDTO2.setId(devoirDTO1.getId());
        assertThat(devoirDTO1).isEqualTo(devoirDTO2);
        devoirDTO2.setId(2L);
        assertThat(devoirDTO1).isNotEqualTo(devoirDTO2);
        devoirDTO1.setId(null);
        assertThat(devoirDTO1).isNotEqualTo(devoirDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(devoirMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(devoirMapper.fromId(null)).isNull();
    }
}
