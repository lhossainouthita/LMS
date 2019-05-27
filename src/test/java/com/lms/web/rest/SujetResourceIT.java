package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Sujet;
import com.lms.repository.SujetRepository;
import com.lms.service.SujetService;
import com.lms.service.dto.SujetDTO;
import com.lms.service.mapper.SujetMapper;
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
 * Integration tests for the {@Link SujetResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class SujetResourceIT {

    private static final String DEFAULT_INTITULE_SUJET = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_SUJET = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_SUJET = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_SUJET = "BBBBBBBBBB";

    @Autowired
    private SujetRepository sujetRepository;

    @Autowired
    private SujetMapper sujetMapper;

    @Autowired
    private SujetService sujetService;

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

    private MockMvc restSujetMockMvc;

    private Sujet sujet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SujetResource sujetResource = new SujetResource(sujetService);
        this.restSujetMockMvc = MockMvcBuilders.standaloneSetup(sujetResource)
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
    public static Sujet createEntity(EntityManager em) {
        Sujet sujet = new Sujet()
            .intituleSujet(DEFAULT_INTITULE_SUJET)
            .descriptionSujet(DEFAULT_DESCRIPTION_SUJET);
        return sujet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sujet createUpdatedEntity(EntityManager em) {
        Sujet sujet = new Sujet()
            .intituleSujet(UPDATED_INTITULE_SUJET)
            .descriptionSujet(UPDATED_DESCRIPTION_SUJET);
        return sujet;
    }

    @BeforeEach
    public void initTest() {
        sujet = createEntity(em);
    }

    @Test
    @Transactional
    public void createSujet() throws Exception {
        int databaseSizeBeforeCreate = sujetRepository.findAll().size();

        // Create the Sujet
        SujetDTO sujetDTO = sujetMapper.toDto(sujet);
        restSujetMockMvc.perform(post("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujetDTO)))
            .andExpect(status().isCreated());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeCreate + 1);
        Sujet testSujet = sujetList.get(sujetList.size() - 1);
        assertThat(testSujet.getIntituleSujet()).isEqualTo(DEFAULT_INTITULE_SUJET);
        assertThat(testSujet.getDescriptionSujet()).isEqualTo(DEFAULT_DESCRIPTION_SUJET);
    }

    @Test
    @Transactional
    public void createSujetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sujetRepository.findAll().size();

        // Create the Sujet with an existing ID
        sujet.setId(1L);
        SujetDTO sujetDTO = sujetMapper.toDto(sujet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSujetMockMvc.perform(post("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIntituleSujetIsRequired() throws Exception {
        int databaseSizeBeforeTest = sujetRepository.findAll().size();
        // set the field null
        sujet.setIntituleSujet(null);

        // Create the Sujet, which fails.
        SujetDTO sujetDTO = sujetMapper.toDto(sujet);

        restSujetMockMvc.perform(post("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujetDTO)))
            .andExpect(status().isBadRequest());

        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSujets() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        // Get all the sujetList
        restSujetMockMvc.perform(get("/api/sujets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sujet.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleSujet").value(hasItem(DEFAULT_INTITULE_SUJET.toString())))
            .andExpect(jsonPath("$.[*].descriptionSujet").value(hasItem(DEFAULT_DESCRIPTION_SUJET.toString())));
    }
    
    @Test
    @Transactional
    public void getSujet() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        // Get the sujet
        restSujetMockMvc.perform(get("/api/sujets/{id}", sujet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sujet.getId().intValue()))
            .andExpect(jsonPath("$.intituleSujet").value(DEFAULT_INTITULE_SUJET.toString()))
            .andExpect(jsonPath("$.descriptionSujet").value(DEFAULT_DESCRIPTION_SUJET.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSujet() throws Exception {
        // Get the sujet
        restSujetMockMvc.perform(get("/api/sujets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSujet() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        int databaseSizeBeforeUpdate = sujetRepository.findAll().size();

        // Update the sujet
        Sujet updatedSujet = sujetRepository.findById(sujet.getId()).get();
        // Disconnect from session so that the updates on updatedSujet are not directly saved in db
        em.detach(updatedSujet);
        updatedSujet
            .intituleSujet(UPDATED_INTITULE_SUJET)
            .descriptionSujet(UPDATED_DESCRIPTION_SUJET);
        SujetDTO sujetDTO = sujetMapper.toDto(updatedSujet);

        restSujetMockMvc.perform(put("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujetDTO)))
            .andExpect(status().isOk());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeUpdate);
        Sujet testSujet = sujetList.get(sujetList.size() - 1);
        assertThat(testSujet.getIntituleSujet()).isEqualTo(UPDATED_INTITULE_SUJET);
        assertThat(testSujet.getDescriptionSujet()).isEqualTo(UPDATED_DESCRIPTION_SUJET);
    }

    @Test
    @Transactional
    public void updateNonExistingSujet() throws Exception {
        int databaseSizeBeforeUpdate = sujetRepository.findAll().size();

        // Create the Sujet
        SujetDTO sujetDTO = sujetMapper.toDto(sujet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSujetMockMvc.perform(put("/api/sujets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sujetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sujet in the database
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSujet() throws Exception {
        // Initialize the database
        sujetRepository.saveAndFlush(sujet);

        int databaseSizeBeforeDelete = sujetRepository.findAll().size();

        // Delete the sujet
        restSujetMockMvc.perform(delete("/api/sujets/{id}", sujet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Sujet> sujetList = sujetRepository.findAll();
        assertThat(sujetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sujet.class);
        Sujet sujet1 = new Sujet();
        sujet1.setId(1L);
        Sujet sujet2 = new Sujet();
        sujet2.setId(sujet1.getId());
        assertThat(sujet1).isEqualTo(sujet2);
        sujet2.setId(2L);
        assertThat(sujet1).isNotEqualTo(sujet2);
        sujet1.setId(null);
        assertThat(sujet1).isNotEqualTo(sujet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SujetDTO.class);
        SujetDTO sujetDTO1 = new SujetDTO();
        sujetDTO1.setId(1L);
        SujetDTO sujetDTO2 = new SujetDTO();
        assertThat(sujetDTO1).isNotEqualTo(sujetDTO2);
        sujetDTO2.setId(sujetDTO1.getId());
        assertThat(sujetDTO1).isEqualTo(sujetDTO2);
        sujetDTO2.setId(2L);
        assertThat(sujetDTO1).isNotEqualTo(sujetDTO2);
        sujetDTO1.setId(null);
        assertThat(sujetDTO1).isNotEqualTo(sujetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sujetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sujetMapper.fromId(null)).isNull();
    }
}
