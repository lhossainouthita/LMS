package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Ressource;
import com.lms.repository.RessourceRepository;
import com.lms.service.RessourceService;
import com.lms.service.dto.RessourceDTO;
import com.lms.service.mapper.RessourceMapper;
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

import com.lms.domain.enumeration.TypeRessource;
/**
 * Integration tests for the {@Link RessourceResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class RessourceResourceIT {

    private static final String DEFAULT_TITRE_RESSOURCE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_RESSOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CHEMIN_RESSOURCE = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN_RESSOURCE = "BBBBBBBBBB";

    private static final TypeRessource DEFAULT_TYPE_RESSOURCE = TypeRessource.VEDIO;
    private static final TypeRessource UPDATED_TYPE_RESSOURCE = TypeRessource.IMAGE;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private RessourceMapper ressourceMapper;

    @Autowired
    private RessourceService ressourceService;

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

    private MockMvc restRessourceMockMvc;

    private Ressource ressource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RessourceResource ressourceResource = new RessourceResource(ressourceService);
        this.restRessourceMockMvc = MockMvcBuilders.standaloneSetup(ressourceResource)
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
    public static Ressource createEntity(EntityManager em) {
        Ressource ressource = new Ressource()
            .titreRessource(DEFAULT_TITRE_RESSOURCE)
            .cheminRessource(DEFAULT_CHEMIN_RESSOURCE)
            .typeRessource(DEFAULT_TYPE_RESSOURCE);
        return ressource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ressource createUpdatedEntity(EntityManager em) {
        Ressource ressource = new Ressource()
            .titreRessource(UPDATED_TITRE_RESSOURCE)
            .cheminRessource(UPDATED_CHEMIN_RESSOURCE)
            .typeRessource(UPDATED_TYPE_RESSOURCE);
        return ressource;
    }

    @BeforeEach
    public void initTest() {
        ressource = createEntity(em);
    }

    @Test
    @Transactional
    public void createRessource() throws Exception {
        int databaseSizeBeforeCreate = ressourceRepository.findAll().size();

        // Create the Ressource
        RessourceDTO ressourceDTO = ressourceMapper.toDto(ressource);
        restRessourceMockMvc.perform(post("/api/ressources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ressourceDTO)))
            .andExpect(status().isCreated());

        // Validate the Ressource in the database
        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeCreate + 1);
        Ressource testRessource = ressourceList.get(ressourceList.size() - 1);
        assertThat(testRessource.getTitreRessource()).isEqualTo(DEFAULT_TITRE_RESSOURCE);
        assertThat(testRessource.getCheminRessource()).isEqualTo(DEFAULT_CHEMIN_RESSOURCE);
        assertThat(testRessource.getTypeRessource()).isEqualTo(DEFAULT_TYPE_RESSOURCE);
    }

    @Test
    @Transactional
    public void createRessourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ressourceRepository.findAll().size();

        // Create the Ressource with an existing ID
        ressource.setId(1L);
        RessourceDTO ressourceDTO = ressourceMapper.toDto(ressource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRessourceMockMvc.perform(post("/api/ressources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ressourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ressource in the database
        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitreRessourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ressourceRepository.findAll().size();
        // set the field null
        ressource.setTitreRessource(null);

        // Create the Ressource, which fails.
        RessourceDTO ressourceDTO = ressourceMapper.toDto(ressource);

        restRessourceMockMvc.perform(post("/api/ressources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ressourceDTO)))
            .andExpect(status().isBadRequest());

        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheminRessourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ressourceRepository.findAll().size();
        // set the field null
        ressource.setCheminRessource(null);

        // Create the Ressource, which fails.
        RessourceDTO ressourceDTO = ressourceMapper.toDto(ressource);

        restRessourceMockMvc.perform(post("/api/ressources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ressourceDTO)))
            .andExpect(status().isBadRequest());

        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRessources() throws Exception {
        // Initialize the database
        ressourceRepository.saveAndFlush(ressource);

        // Get all the ressourceList
        restRessourceMockMvc.perform(get("/api/ressources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ressource.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreRessource").value(hasItem(DEFAULT_TITRE_RESSOURCE.toString())))
            .andExpect(jsonPath("$.[*].cheminRessource").value(hasItem(DEFAULT_CHEMIN_RESSOURCE.toString())))
            .andExpect(jsonPath("$.[*].typeRessource").value(hasItem(DEFAULT_TYPE_RESSOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getRessource() throws Exception {
        // Initialize the database
        ressourceRepository.saveAndFlush(ressource);

        // Get the ressource
        restRessourceMockMvc.perform(get("/api/ressources/{id}", ressource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ressource.getId().intValue()))
            .andExpect(jsonPath("$.titreRessource").value(DEFAULT_TITRE_RESSOURCE.toString()))
            .andExpect(jsonPath("$.cheminRessource").value(DEFAULT_CHEMIN_RESSOURCE.toString()))
            .andExpect(jsonPath("$.typeRessource").value(DEFAULT_TYPE_RESSOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRessource() throws Exception {
        // Get the ressource
        restRessourceMockMvc.perform(get("/api/ressources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRessource() throws Exception {
        // Initialize the database
        ressourceRepository.saveAndFlush(ressource);

        int databaseSizeBeforeUpdate = ressourceRepository.findAll().size();

        // Update the ressource
        Ressource updatedRessource = ressourceRepository.findById(ressource.getId()).get();
        // Disconnect from session so that the updates on updatedRessource are not directly saved in db
        em.detach(updatedRessource);
        updatedRessource
            .titreRessource(UPDATED_TITRE_RESSOURCE)
            .cheminRessource(UPDATED_CHEMIN_RESSOURCE)
            .typeRessource(UPDATED_TYPE_RESSOURCE);
        RessourceDTO ressourceDTO = ressourceMapper.toDto(updatedRessource);

        restRessourceMockMvc.perform(put("/api/ressources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ressourceDTO)))
            .andExpect(status().isOk());

        // Validate the Ressource in the database
        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeUpdate);
        Ressource testRessource = ressourceList.get(ressourceList.size() - 1);
        assertThat(testRessource.getTitreRessource()).isEqualTo(UPDATED_TITRE_RESSOURCE);
        assertThat(testRessource.getCheminRessource()).isEqualTo(UPDATED_CHEMIN_RESSOURCE);
        assertThat(testRessource.getTypeRessource()).isEqualTo(UPDATED_TYPE_RESSOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingRessource() throws Exception {
        int databaseSizeBeforeUpdate = ressourceRepository.findAll().size();

        // Create the Ressource
        RessourceDTO ressourceDTO = ressourceMapper.toDto(ressource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRessourceMockMvc.perform(put("/api/ressources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ressourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ressource in the database
        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRessource() throws Exception {
        // Initialize the database
        ressourceRepository.saveAndFlush(ressource);

        int databaseSizeBeforeDelete = ressourceRepository.findAll().size();

        // Delete the ressource
        restRessourceMockMvc.perform(delete("/api/ressources/{id}", ressource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Ressource> ressourceList = ressourceRepository.findAll();
        assertThat(ressourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ressource.class);
        Ressource ressource1 = new Ressource();
        ressource1.setId(1L);
        Ressource ressource2 = new Ressource();
        ressource2.setId(ressource1.getId());
        assertThat(ressource1).isEqualTo(ressource2);
        ressource2.setId(2L);
        assertThat(ressource1).isNotEqualTo(ressource2);
        ressource1.setId(null);
        assertThat(ressource1).isNotEqualTo(ressource2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RessourceDTO.class);
        RessourceDTO ressourceDTO1 = new RessourceDTO();
        ressourceDTO1.setId(1L);
        RessourceDTO ressourceDTO2 = new RessourceDTO();
        assertThat(ressourceDTO1).isNotEqualTo(ressourceDTO2);
        ressourceDTO2.setId(ressourceDTO1.getId());
        assertThat(ressourceDTO1).isEqualTo(ressourceDTO2);
        ressourceDTO2.setId(2L);
        assertThat(ressourceDTO1).isNotEqualTo(ressourceDTO2);
        ressourceDTO1.setId(null);
        assertThat(ressourceDTO1).isNotEqualTo(ressourceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ressourceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ressourceMapper.fromId(null)).isNull();
    }
}
