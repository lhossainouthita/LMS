package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.Periode;
import com.lms.repository.PeriodeRepository;
import com.lms.service.PeriodeService;
import com.lms.service.dto.PeriodeDTO;
import com.lms.service.mapper.PeriodeMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.lms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PeriodeResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class PeriodeResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PeriodeRepository periodeRepository;

    @Autowired
    private PeriodeMapper periodeMapper;

    @Autowired
    private PeriodeService periodeService;

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

    private MockMvc restPeriodeMockMvc;

    private Periode periode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodeResource periodeResource = new PeriodeResource(periodeService);
        this.restPeriodeMockMvc = MockMvcBuilders.standaloneSetup(periodeResource)
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
    public static Periode createEntity(EntityManager em) {
        Periode periode = new Periode()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return periode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periode createUpdatedEntity(EntityManager em) {
        Periode periode = new Periode()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        return periode;
    }

    @BeforeEach
    public void initTest() {
        periode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriode() throws Exception {
        int databaseSizeBeforeCreate = periodeRepository.findAll().size();

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);
        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isCreated());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeCreate + 1);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPeriode.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createPeriodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodeRepository.findAll().size();

        // Create the Periode with an existing ID
        periode.setId(1L);
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setDateDebut(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodeRepository.findAll().size();
        // set the field null
        periode.setDateFin(null);

        // Create the Periode, which fails.
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        restPeriodeMockMvc.perform(post("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodes() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get all the periodeList
        restPeriodeMockMvc.perform(get("/api/periodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periode.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        // Get the periode
        restPeriodeMockMvc.perform(get("/api/periodes/{id}", periode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periode.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriode() throws Exception {
        // Get the periode
        restPeriodeMockMvc.perform(get("/api/periodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Update the periode
        Periode updatedPeriode = periodeRepository.findById(periode.getId()).get();
        // Disconnect from session so that the updates on updatedPeriode are not directly saved in db
        em.detach(updatedPeriode);
        updatedPeriode
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        PeriodeDTO periodeDTO = periodeMapper.toDto(updatedPeriode);

        restPeriodeMockMvc.perform(put("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isOk());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
        Periode testPeriode = periodeList.get(periodeList.size() - 1);
        assertThat(testPeriode.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPeriode.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriode() throws Exception {
        int databaseSizeBeforeUpdate = periodeRepository.findAll().size();

        // Create the Periode
        PeriodeDTO periodeDTO = periodeMapper.toDto(periode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodeMockMvc.perform(put("/api/periodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periode in the database
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriode() throws Exception {
        // Initialize the database
        periodeRepository.saveAndFlush(periode);

        int databaseSizeBeforeDelete = periodeRepository.findAll().size();

        // Delete the periode
        restPeriodeMockMvc.perform(delete("/api/periodes/{id}", periode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Periode> periodeList = periodeRepository.findAll();
        assertThat(periodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periode.class);
        Periode periode1 = new Periode();
        periode1.setId(1L);
        Periode periode2 = new Periode();
        periode2.setId(periode1.getId());
        assertThat(periode1).isEqualTo(periode2);
        periode2.setId(2L);
        assertThat(periode1).isNotEqualTo(periode2);
        periode1.setId(null);
        assertThat(periode1).isNotEqualTo(periode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodeDTO.class);
        PeriodeDTO periodeDTO1 = new PeriodeDTO();
        periodeDTO1.setId(1L);
        PeriodeDTO periodeDTO2 = new PeriodeDTO();
        assertThat(periodeDTO1).isNotEqualTo(periodeDTO2);
        periodeDTO2.setId(periodeDTO1.getId());
        assertThat(periodeDTO1).isEqualTo(periodeDTO2);
        periodeDTO2.setId(2L);
        assertThat(periodeDTO1).isNotEqualTo(periodeDTO2);
        periodeDTO1.setId(null);
        assertThat(periodeDTO1).isNotEqualTo(periodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodeMapper.fromId(null)).isNull();
    }
}
