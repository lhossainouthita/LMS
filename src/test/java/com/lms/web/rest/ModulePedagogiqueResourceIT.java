package com.lms.web.rest;

import com.lms.LmsApp;
import com.lms.domain.ModulePedagogique;
import com.lms.repository.ModulePedagogiqueRepository;
import com.lms.service.ModulePedagogiqueService;
import com.lms.service.dto.ModulePedagogiqueDTO;
import com.lms.service.mapper.ModulePedagogiqueMapper;
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
 * Integration tests for the {@Link ModulePedagogiqueResource} REST controller.
 */
@SpringBootTest(classes = LmsApp.class)
public class ModulePedagogiqueResourceIT {

    private static final String DEFAULT_CODE_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_INTITULE_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_MODULE = "BBBBBBBBBB";

    @Autowired
    private ModulePedagogiqueRepository modulePedagogiqueRepository;

    @Autowired
    private ModulePedagogiqueMapper modulePedagogiqueMapper;

    @Autowired
    private ModulePedagogiqueService modulePedagogiqueService;

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

    private MockMvc restModulePedagogiqueMockMvc;

    private ModulePedagogique modulePedagogique;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModulePedagogiqueResource modulePedagogiqueResource = new ModulePedagogiqueResource(modulePedagogiqueService);
        this.restModulePedagogiqueMockMvc = MockMvcBuilders.standaloneSetup(modulePedagogiqueResource)
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
    public static ModulePedagogique createEntity(EntityManager em) {
        ModulePedagogique modulePedagogique = new ModulePedagogique()
            .codeModule(DEFAULT_CODE_MODULE)
            .intituleModule(DEFAULT_INTITULE_MODULE)
            .descriptionModule(DEFAULT_DESCRIPTION_MODULE);
        return modulePedagogique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModulePedagogique createUpdatedEntity(EntityManager em) {
        ModulePedagogique modulePedagogique = new ModulePedagogique()
            .codeModule(UPDATED_CODE_MODULE)
            .intituleModule(UPDATED_INTITULE_MODULE)
            .descriptionModule(UPDATED_DESCRIPTION_MODULE);
        return modulePedagogique;
    }

    @BeforeEach
    public void initTest() {
        modulePedagogique = createEntity(em);
    }

    @Test
    @Transactional
    public void createModulePedagogique() throws Exception {
        int databaseSizeBeforeCreate = modulePedagogiqueRepository.findAll().size();

        // Create the ModulePedagogique
        ModulePedagogiqueDTO modulePedagogiqueDTO = modulePedagogiqueMapper.toDto(modulePedagogique);
        restModulePedagogiqueMockMvc.perform(post("/api/module-pedagogiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulePedagogiqueDTO)))
            .andExpect(status().isCreated());

        // Validate the ModulePedagogique in the database
        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeCreate + 1);
        ModulePedagogique testModulePedagogique = modulePedagogiqueList.get(modulePedagogiqueList.size() - 1);
        assertThat(testModulePedagogique.getCodeModule()).isEqualTo(DEFAULT_CODE_MODULE);
        assertThat(testModulePedagogique.getIntituleModule()).isEqualTo(DEFAULT_INTITULE_MODULE);
        assertThat(testModulePedagogique.getDescriptionModule()).isEqualTo(DEFAULT_DESCRIPTION_MODULE);
    }

    @Test
    @Transactional
    public void createModulePedagogiqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modulePedagogiqueRepository.findAll().size();

        // Create the ModulePedagogique with an existing ID
        modulePedagogique.setId(1L);
        ModulePedagogiqueDTO modulePedagogiqueDTO = modulePedagogiqueMapper.toDto(modulePedagogique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModulePedagogiqueMockMvc.perform(post("/api/module-pedagogiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulePedagogiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModulePedagogique in the database
        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeModuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = modulePedagogiqueRepository.findAll().size();
        // set the field null
        modulePedagogique.setCodeModule(null);

        // Create the ModulePedagogique, which fails.
        ModulePedagogiqueDTO modulePedagogiqueDTO = modulePedagogiqueMapper.toDto(modulePedagogique);

        restModulePedagogiqueMockMvc.perform(post("/api/module-pedagogiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulePedagogiqueDTO)))
            .andExpect(status().isBadRequest());

        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntituleModuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = modulePedagogiqueRepository.findAll().size();
        // set the field null
        modulePedagogique.setIntituleModule(null);

        // Create the ModulePedagogique, which fails.
        ModulePedagogiqueDTO modulePedagogiqueDTO = modulePedagogiqueMapper.toDto(modulePedagogique);

        restModulePedagogiqueMockMvc.perform(post("/api/module-pedagogiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulePedagogiqueDTO)))
            .andExpect(status().isBadRequest());

        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModulePedagogiques() throws Exception {
        // Initialize the database
        modulePedagogiqueRepository.saveAndFlush(modulePedagogique);

        // Get all the modulePedagogiqueList
        restModulePedagogiqueMockMvc.perform(get("/api/module-pedagogiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulePedagogique.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeModule").value(hasItem(DEFAULT_CODE_MODULE.toString())))
            .andExpect(jsonPath("$.[*].intituleModule").value(hasItem(DEFAULT_INTITULE_MODULE.toString())))
            .andExpect(jsonPath("$.[*].descriptionModule").value(hasItem(DEFAULT_DESCRIPTION_MODULE.toString())));
    }
    
    @Test
    @Transactional
    public void getModulePedagogique() throws Exception {
        // Initialize the database
        modulePedagogiqueRepository.saveAndFlush(modulePedagogique);

        // Get the modulePedagogique
        restModulePedagogiqueMockMvc.perform(get("/api/module-pedagogiques/{id}", modulePedagogique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modulePedagogique.getId().intValue()))
            .andExpect(jsonPath("$.codeModule").value(DEFAULT_CODE_MODULE.toString()))
            .andExpect(jsonPath("$.intituleModule").value(DEFAULT_INTITULE_MODULE.toString()))
            .andExpect(jsonPath("$.descriptionModule").value(DEFAULT_DESCRIPTION_MODULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModulePedagogique() throws Exception {
        // Get the modulePedagogique
        restModulePedagogiqueMockMvc.perform(get("/api/module-pedagogiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModulePedagogique() throws Exception {
        // Initialize the database
        modulePedagogiqueRepository.saveAndFlush(modulePedagogique);

        int databaseSizeBeforeUpdate = modulePedagogiqueRepository.findAll().size();

        // Update the modulePedagogique
        ModulePedagogique updatedModulePedagogique = modulePedagogiqueRepository.findById(modulePedagogique.getId()).get();
        // Disconnect from session so that the updates on updatedModulePedagogique are not directly saved in db
        em.detach(updatedModulePedagogique);
        updatedModulePedagogique
            .codeModule(UPDATED_CODE_MODULE)
            .intituleModule(UPDATED_INTITULE_MODULE)
            .descriptionModule(UPDATED_DESCRIPTION_MODULE);
        ModulePedagogiqueDTO modulePedagogiqueDTO = modulePedagogiqueMapper.toDto(updatedModulePedagogique);

        restModulePedagogiqueMockMvc.perform(put("/api/module-pedagogiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulePedagogiqueDTO)))
            .andExpect(status().isOk());

        // Validate the ModulePedagogique in the database
        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeUpdate);
        ModulePedagogique testModulePedagogique = modulePedagogiqueList.get(modulePedagogiqueList.size() - 1);
        assertThat(testModulePedagogique.getCodeModule()).isEqualTo(UPDATED_CODE_MODULE);
        assertThat(testModulePedagogique.getIntituleModule()).isEqualTo(UPDATED_INTITULE_MODULE);
        assertThat(testModulePedagogique.getDescriptionModule()).isEqualTo(UPDATED_DESCRIPTION_MODULE);
    }

    @Test
    @Transactional
    public void updateNonExistingModulePedagogique() throws Exception {
        int databaseSizeBeforeUpdate = modulePedagogiqueRepository.findAll().size();

        // Create the ModulePedagogique
        ModulePedagogiqueDTO modulePedagogiqueDTO = modulePedagogiqueMapper.toDto(modulePedagogique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModulePedagogiqueMockMvc.perform(put("/api/module-pedagogiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modulePedagogiqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModulePedagogique in the database
        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModulePedagogique() throws Exception {
        // Initialize the database
        modulePedagogiqueRepository.saveAndFlush(modulePedagogique);

        int databaseSizeBeforeDelete = modulePedagogiqueRepository.findAll().size();

        // Delete the modulePedagogique
        restModulePedagogiqueMockMvc.perform(delete("/api/module-pedagogiques/{id}", modulePedagogique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ModulePedagogique> modulePedagogiqueList = modulePedagogiqueRepository.findAll();
        assertThat(modulePedagogiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModulePedagogique.class);
        ModulePedagogique modulePedagogique1 = new ModulePedagogique();
        modulePedagogique1.setId(1L);
        ModulePedagogique modulePedagogique2 = new ModulePedagogique();
        modulePedagogique2.setId(modulePedagogique1.getId());
        assertThat(modulePedagogique1).isEqualTo(modulePedagogique2);
        modulePedagogique2.setId(2L);
        assertThat(modulePedagogique1).isNotEqualTo(modulePedagogique2);
        modulePedagogique1.setId(null);
        assertThat(modulePedagogique1).isNotEqualTo(modulePedagogique2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModulePedagogiqueDTO.class);
        ModulePedagogiqueDTO modulePedagogiqueDTO1 = new ModulePedagogiqueDTO();
        modulePedagogiqueDTO1.setId(1L);
        ModulePedagogiqueDTO modulePedagogiqueDTO2 = new ModulePedagogiqueDTO();
        assertThat(modulePedagogiqueDTO1).isNotEqualTo(modulePedagogiqueDTO2);
        modulePedagogiqueDTO2.setId(modulePedagogiqueDTO1.getId());
        assertThat(modulePedagogiqueDTO1).isEqualTo(modulePedagogiqueDTO2);
        modulePedagogiqueDTO2.setId(2L);
        assertThat(modulePedagogiqueDTO1).isNotEqualTo(modulePedagogiqueDTO2);
        modulePedagogiqueDTO1.setId(null);
        assertThat(modulePedagogiqueDTO1).isNotEqualTo(modulePedagogiqueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modulePedagogiqueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modulePedagogiqueMapper.fromId(null)).isNull();
    }
}
