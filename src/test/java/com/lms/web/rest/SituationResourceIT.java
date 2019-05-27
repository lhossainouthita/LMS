package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Situation;
import com.lms.repository.SituationRepository;
import com.lms.service.SituationService;
import com.lms.service.dto.SituationDTO;
import com.lms.service.mapper.SituationMapper;
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
 * Integration tests for the {@Link SituationResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class SituationResourceIT {

    private static final String DEFAULT_TITRE_SITUATION = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_SITUATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENU_SITUATION = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU_SITUATION = "BBBBBBBBBB";

    @Autowired
    private SituationRepository situationRepository;

    @Autowired
    private SituationMapper situationMapper;

    @Autowired
    private SituationService situationService;

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

    private MockMvc restSituationMockMvc;

    private Situation situation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SituationResource situationResource = new SituationResource(situationService);
        this.restSituationMockMvc = MockMvcBuilders.standaloneSetup(situationResource)
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
    public static Situation createEntity(EntityManager em) {
        Situation situation = new Situation()
            .titreSituation(DEFAULT_TITRE_SITUATION)
            .contenuSituation(DEFAULT_CONTENU_SITUATION);
        return situation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createUpdatedEntity(EntityManager em) {
        Situation situation = new Situation()
            .titreSituation(UPDATED_TITRE_SITUATION)
            .contenuSituation(UPDATED_CONTENU_SITUATION);
        return situation;
    }

    @BeforeEach
    public void initTest() {
        situation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituation() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);
        restSituationMockMvc.perform(post("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationDTO)))
            .andExpect(status().isCreated());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate + 1);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getTitreSituation()).isEqualTo(DEFAULT_TITRE_SITUATION);
        assertThat(testSituation.getContenuSituation()).isEqualTo(DEFAULT_CONTENU_SITUATION);
    }

    @Test
    @Transactional
    public void createSituationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();

        // Create the Situation with an existing ID
        situation.setId(1L);
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMockMvc.perform(post("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitreSituationIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationRepository.findAll().size();
        // set the field null
        situation.setTitreSituation(null);

        // Create the Situation, which fails.
        SituationDTO situationDTO = situationMapper.toDto(situation);

        restSituationMockMvc.perform(post("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationDTO)))
            .andExpect(status().isBadRequest());

        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContenuSituationIsRequired() throws Exception {
        int databaseSizeBeforeTest = situationRepository.findAll().size();
        // set the field null
        situation.setContenuSituation(null);

        // Create the Situation, which fails.
        SituationDTO situationDTO = situationMapper.toDto(situation);

        restSituationMockMvc.perform(post("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationDTO)))
            .andExpect(status().isBadRequest());

        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSituations() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get all the situationList
        restSituationMockMvc.perform(get("/api/situations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situation.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreSituation").value(hasItem(DEFAULT_TITRE_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].contenuSituation").value(hasItem(DEFAULT_CONTENU_SITUATION.toString())));
    }
    
    @Test
    @Transactional
    public void getSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get the situation
        restSituationMockMvc.perform(get("/api/situations/{id}", situation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(situation.getId().intValue()))
            .andExpect(jsonPath("$.titreSituation").value(DEFAULT_TITRE_SITUATION.toString()))
            .andExpect(jsonPath("$.contenuSituation").value(DEFAULT_CONTENU_SITUATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSituation() throws Exception {
        // Get the situation
        restSituationMockMvc.perform(get("/api/situations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Update the situation
        Situation updatedSituation = situationRepository.findById(situation.getId()).get();
        // Disconnect from session so that the updates on updatedSituation are not directly saved in db
        em.detach(updatedSituation);
        updatedSituation
            .titreSituation(UPDATED_TITRE_SITUATION)
            .contenuSituation(UPDATED_CONTENU_SITUATION);
        SituationDTO situationDTO = situationMapper.toDto(updatedSituation);

        restSituationMockMvc.perform(put("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationDTO)))
            .andExpect(status().isOk());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getTitreSituation()).isEqualTo(UPDATED_TITRE_SITUATION);
        assertThat(testSituation.getContenuSituation()).isEqualTo(UPDATED_CONTENU_SITUATION);
    }

    @Test
    @Transactional
    public void updateNonExistingSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Create the Situation
        SituationDTO situationDTO = situationMapper.toDto(situation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc.perform(put("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeDelete = situationRepository.findAll().size();

        // Delete the situation
        restSituationMockMvc.perform(delete("/api/situations/{id}", situation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Situation.class);
        Situation situation1 = new Situation();
        situation1.setId(1L);
        Situation situation2 = new Situation();
        situation2.setId(situation1.getId());
        assertThat(situation1).isEqualTo(situation2);
        situation2.setId(2L);
        assertThat(situation1).isNotEqualTo(situation2);
        situation1.setId(null);
        assertThat(situation1).isNotEqualTo(situation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituationDTO.class);
        SituationDTO situationDTO1 = new SituationDTO();
        situationDTO1.setId(1L);
        SituationDTO situationDTO2 = new SituationDTO();
        assertThat(situationDTO1).isNotEqualTo(situationDTO2);
        situationDTO2.setId(situationDTO1.getId());
        assertThat(situationDTO1).isEqualTo(situationDTO2);
        situationDTO2.setId(2L);
        assertThat(situationDTO1).isNotEqualTo(situationDTO2);
        situationDTO1.setId(null);
        assertThat(situationDTO1).isNotEqualTo(situationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(situationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(situationMapper.fromId(null)).isNull();
    }
}
