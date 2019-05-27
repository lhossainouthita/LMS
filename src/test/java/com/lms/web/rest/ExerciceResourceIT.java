package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Exercice;
import com.lms.repository.ExerciceRepository;
import com.lms.service.ExerciceService;
import com.lms.service.dto.ExerciceDTO;
import com.lms.service.mapper.ExerciceMapper;
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
 * Integration tests for the {@Link ExerciceResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class ExerciceResourceIT {

    private static final String DEFAULT_TITRE_EXERCICE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE_EXERCICE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENU_EXERCICE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU_EXERCICE = "BBBBBBBBBB";

    @Autowired
    private ExerciceRepository exerciceRepository;

    @Autowired
    private ExerciceMapper exerciceMapper;

    @Autowired
    private ExerciceService exerciceService;

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

    private MockMvc restExerciceMockMvc;

    private Exercice exercice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExerciceResource exerciceResource = new ExerciceResource(exerciceService);
        this.restExerciceMockMvc = MockMvcBuilders.standaloneSetup(exerciceResource)
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
    public static Exercice createEntity(EntityManager em) {
        Exercice exercice = new Exercice()
            .titreExercice(DEFAULT_TITRE_EXERCICE)
            .contenuExercice(DEFAULT_CONTENU_EXERCICE);
        return exercice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exercice createUpdatedEntity(EntityManager em) {
        Exercice exercice = new Exercice()
            .titreExercice(UPDATED_TITRE_EXERCICE)
            .contenuExercice(UPDATED_CONTENU_EXERCICE);
        return exercice;
    }

    @BeforeEach
    public void initTest() {
        exercice = createEntity(em);
    }

    @Test
    @Transactional
    public void createExercice() throws Exception {
        int databaseSizeBeforeCreate = exerciceRepository.findAll().size();

        // Create the Exercice
        ExerciceDTO exerciceDTO = exerciceMapper.toDto(exercice);
        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciceDTO)))
            .andExpect(status().isCreated());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeCreate + 1);
        Exercice testExercice = exerciceList.get(exerciceList.size() - 1);
        assertThat(testExercice.getTitreExercice()).isEqualTo(DEFAULT_TITRE_EXERCICE);
        assertThat(testExercice.getContenuExercice()).isEqualTo(DEFAULT_CONTENU_EXERCICE);
    }

    @Test
    @Transactional
    public void createExerciceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciceRepository.findAll().size();

        // Create the Exercice with an existing ID
        exercice.setId(1L);
        ExerciceDTO exerciceDTO = exerciceMapper.toDto(exercice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitreExerciceIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciceRepository.findAll().size();
        // set the field null
        exercice.setTitreExercice(null);

        // Create the Exercice, which fails.
        ExerciceDTO exerciceDTO = exerciceMapper.toDto(exercice);

        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciceDTO)))
            .andExpect(status().isBadRequest());

        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContenuExerciceIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciceRepository.findAll().size();
        // set the field null
        exercice.setContenuExercice(null);

        // Create the Exercice, which fails.
        ExerciceDTO exerciceDTO = exerciceMapper.toDto(exercice);

        restExerciceMockMvc.perform(post("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciceDTO)))
            .andExpect(status().isBadRequest());

        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExercices() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        // Get all the exerciceList
        restExerciceMockMvc.perform(get("/api/exercices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exercice.getId().intValue())))
            .andExpect(jsonPath("$.[*].titreExercice").value(hasItem(DEFAULT_TITRE_EXERCICE.toString())))
            .andExpect(jsonPath("$.[*].contenuExercice").value(hasItem(DEFAULT_CONTENU_EXERCICE.toString())));
    }
    
    @Test
    @Transactional
    public void getExercice() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        // Get the exercice
        restExerciceMockMvc.perform(get("/api/exercices/{id}", exercice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exercice.getId().intValue()))
            .andExpect(jsonPath("$.titreExercice").value(DEFAULT_TITRE_EXERCICE.toString()))
            .andExpect(jsonPath("$.contenuExercice").value(DEFAULT_CONTENU_EXERCICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExercice() throws Exception {
        // Get the exercice
        restExerciceMockMvc.perform(get("/api/exercices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExercice() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        int databaseSizeBeforeUpdate = exerciceRepository.findAll().size();

        // Update the exercice
        Exercice updatedExercice = exerciceRepository.findById(exercice.getId()).get();
        // Disconnect from session so that the updates on updatedExercice are not directly saved in db
        em.detach(updatedExercice);
        updatedExercice
            .titreExercice(UPDATED_TITRE_EXERCICE)
            .contenuExercice(UPDATED_CONTENU_EXERCICE);
        ExerciceDTO exerciceDTO = exerciceMapper.toDto(updatedExercice);

        restExerciceMockMvc.perform(put("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciceDTO)))
            .andExpect(status().isOk());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeUpdate);
        Exercice testExercice = exerciceList.get(exerciceList.size() - 1);
        assertThat(testExercice.getTitreExercice()).isEqualTo(UPDATED_TITRE_EXERCICE);
        assertThat(testExercice.getContenuExercice()).isEqualTo(UPDATED_CONTENU_EXERCICE);
    }

    @Test
    @Transactional
    public void updateNonExistingExercice() throws Exception {
        int databaseSizeBeforeUpdate = exerciceRepository.findAll().size();

        // Create the Exercice
        ExerciceDTO exerciceDTO = exerciceMapper.toDto(exercice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExerciceMockMvc.perform(put("/api/exercices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exerciceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exercice in the database
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExercice() throws Exception {
        // Initialize the database
        exerciceRepository.saveAndFlush(exercice);

        int databaseSizeBeforeDelete = exerciceRepository.findAll().size();

        // Delete the exercice
        restExerciceMockMvc.perform(delete("/api/exercices/{id}", exercice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Exercice> exerciceList = exerciceRepository.findAll();
        assertThat(exerciceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exercice.class);
        Exercice exercice1 = new Exercice();
        exercice1.setId(1L);
        Exercice exercice2 = new Exercice();
        exercice2.setId(exercice1.getId());
        assertThat(exercice1).isEqualTo(exercice2);
        exercice2.setId(2L);
        assertThat(exercice1).isNotEqualTo(exercice2);
        exercice1.setId(null);
        assertThat(exercice1).isNotEqualTo(exercice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciceDTO.class);
        ExerciceDTO exerciceDTO1 = new ExerciceDTO();
        exerciceDTO1.setId(1L);
        ExerciceDTO exerciceDTO2 = new ExerciceDTO();
        assertThat(exerciceDTO1).isNotEqualTo(exerciceDTO2);
        exerciceDTO2.setId(exerciceDTO1.getId());
        assertThat(exerciceDTO1).isEqualTo(exerciceDTO2);
        exerciceDTO2.setId(2L);
        assertThat(exerciceDTO1).isNotEqualTo(exerciceDTO2);
        exerciceDTO1.setId(null);
        assertThat(exerciceDTO1).isNotEqualTo(exerciceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(exerciceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(exerciceMapper.fromId(null)).isNull();
    }
}
