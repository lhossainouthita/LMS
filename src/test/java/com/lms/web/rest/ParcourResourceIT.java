package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Parcour;
import com.lms.repository.ParcourRepository;
import com.lms.service.ParcourService;
import com.lms.service.dto.ParcourDTO;
import com.lms.service.mapper.ParcourMapper;
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
 * Integration tests for the {@Link ParcourResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class ParcourResourceIT {

    private static final String DEFAULT_TITRE_PARCOUR = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_PARCOUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIVEAU_PARCOUR = 1;
    private static final Integer UPDATED_NIVEAU_PARCOUR = 2;

    @Autowired
    private ParcourRepository parcourRepository;

    @Autowired
    private ParcourMapper parcourMapper;

    @Autowired
    private ParcourService parcourService;

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

    private MockMvc restParcourMockMvc;

    private Parcour parcour;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParcourResource parcourResource = new ParcourResource(parcourService);
        this.restParcourMockMvc = MockMvcBuilders.standaloneSetup(parcourResource)
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
    public static Parcour createEntity(EntityManager em) {
        Parcour parcour = new Parcour()
            .titreParcour(DEFAULT_TITRE_PARCOUR)
            .niveauParcour(DEFAULT_NIVEAU_PARCOUR);
        return parcour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parcour createUpdatedEntity(EntityManager em) {
        Parcour parcour = new Parcour()
            .titreParcour(UPDATED_TITRE_PARCOUR)
            .niveauParcour(UPDATED_NIVEAU_PARCOUR);
        return parcour;
    }

    @BeforeEach
    public void initTest() {
        parcour = createEntity(em);
    }

    @Test
    @Transactional
    public void createParcour() throws Exception {
        int databaseSizeBeforeCreate = parcourRepository.findAll().size();

        // Create the Parcour
        ParcourDTO parcourDTO = parcourMapper.toDto(parcour);
        restParcourMockMvc.perform(post("/api/parcours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcourDTO)))
            .andExpect(status().isCreated());

        // Validate the Parcour in the database
        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeCreate + 1);
        Parcour testParcour = parcourList.get(parcourList.size() - 1);
        assertThat(testParcour.getTitreParcour()).isEqualTo(DEFAULT_TITRE_PARCOUR);
        assertThat(testParcour.getNiveauParcour()).isEqualTo(DEFAULT_NIVEAU_PARCOUR);
    }

    @Test
    @Transactional
    public void createParcourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parcourRepository.findAll().size();

        // Create the Parcour with an existing ID
        parcour.setId(1L);
        ParcourDTO parcourDTO = parcourMapper.toDto(parcour);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParcourMockMvc.perform(post("/api/parcours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parcour in the database
        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitreParcourIsRequired() throws Exception {
        int databaseSizeBeforeTest = parcourRepository.findAll().size();
        // set the field null
        parcour.setTitreParcour(null);

        // Create the Parcour, which fails.
        ParcourDTO parcourDTO = parcourMapper.toDto(parcour);

        restParcourMockMvc.perform(post("/api/parcours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcourDTO)))
            .andExpect(status().isBadRequest());

        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNiveauParcourIsRequired() throws Exception {
        int databaseSizeBeforeTest = parcourRepository.findAll().size();
        // set the field null
        parcour.setNiveauParcour(null);

        // Create the Parcour, which fails.
        ParcourDTO parcourDTO = parcourMapper.toDto(parcour);

        restParcourMockMvc.perform(post("/api/parcours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcourDTO)))
            .andExpect(status().isBadRequest());

        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParcours() throws Exception {
        // Initialize the database
        parcourRepository.saveAndFlush(parcour);

        // Get all the parcourList
        restParcourMockMvc.perform(get("/api/parcours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parcour.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreParcour").value(hasItem(DEFAULT_TITRE_PARCOUR.toString())))
            .andExpect(jsonPath("$.[*].niveauParcour").value(hasItem(DEFAULT_NIVEAU_PARCOUR)));
    }
    
    @Test
    @Transactional
    public void getParcour() throws Exception {
        // Initialize the database
        parcourRepository.saveAndFlush(parcour);

        // Get the parcour
        restParcourMockMvc.perform(get("/api/parcours/{id}", parcour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parcour.getId().intValue()))
            .andExpect(jsonPath("$.titreParcour").value(DEFAULT_TITRE_PARCOUR.toString()))
            .andExpect(jsonPath("$.niveauParcour").value(DEFAULT_NIVEAU_PARCOUR));
    }

    @Test
    @Transactional
    public void getNonExistingParcour() throws Exception {
        // Get the parcour
        restParcourMockMvc.perform(get("/api/parcours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParcour() throws Exception {
        // Initialize the database
        parcourRepository.saveAndFlush(parcour);

        int databaseSizeBeforeUpdate = parcourRepository.findAll().size();

        // Update the parcour
        Parcour updatedParcour = parcourRepository.findById(parcour.getId()).get();
        // Disconnect from session so that the updates on updatedParcour are not directly saved in db
        em.detach(updatedParcour);
        updatedParcour
            .titreParcour(UPDATED_TITRE_PARCOUR)
            .niveauParcour(UPDATED_NIVEAU_PARCOUR);
        ParcourDTO parcourDTO = parcourMapper.toDto(updatedParcour);

        restParcourMockMvc.perform(put("/api/parcours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcourDTO)))
            .andExpect(status().isOk());

        // Validate the Parcour in the database
        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeUpdate);
        Parcour testParcour = parcourList.get(parcourList.size() - 1);
        assertThat(testParcour.getTitreParcour()).isEqualTo(UPDATED_TITRE_PARCOUR);
        assertThat(testParcour.getNiveauParcour()).isEqualTo(UPDATED_NIVEAU_PARCOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingParcour() throws Exception {
        int databaseSizeBeforeUpdate = parcourRepository.findAll().size();

        // Create the Parcour
        ParcourDTO parcourDTO = parcourMapper.toDto(parcour);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParcourMockMvc.perform(put("/api/parcours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parcourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parcour in the database
        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParcour() throws Exception {
        // Initialize the database
        parcourRepository.saveAndFlush(parcour);

        int databaseSizeBeforeDelete = parcourRepository.findAll().size();

        // Delete the parcour
        restParcourMockMvc.perform(delete("/api/parcours/{id}", parcour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Parcour> parcourList = parcourRepository.findAll();
        assertThat(parcourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parcour.class);
        Parcour parcour1 = new Parcour();
        parcour1.setId(1L);
        Parcour parcour2 = new Parcour();
        parcour2.setId(parcour1.getId());
        assertThat(parcour1).isEqualTo(parcour2);
        parcour2.setId(2L);
        assertThat(parcour1).isNotEqualTo(parcour2);
        parcour1.setId(null);
        assertThat(parcour1).isNotEqualTo(parcour2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParcourDTO.class);
        ParcourDTO parcourDTO1 = new ParcourDTO();
        parcourDTO1.setId(1L);
        ParcourDTO parcourDTO2 = new ParcourDTO();
        assertThat(parcourDTO1).isNotEqualTo(parcourDTO2);
        parcourDTO2.setId(parcourDTO1.getId());
        assertThat(parcourDTO1).isEqualTo(parcourDTO2);
        parcourDTO2.setId(2L);
        assertThat(parcourDTO1).isNotEqualTo(parcourDTO2);
        parcourDTO1.setId(null);
        assertThat(parcourDTO1).isNotEqualTo(parcourDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parcourMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parcourMapper.fromId(null)).isNull();
    }
}
