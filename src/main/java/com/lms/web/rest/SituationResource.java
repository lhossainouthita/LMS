package com.lms.web.rest;

import com.lms.service.SituationService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.SituationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lms.domain.Situation}.
 */
@RestController
@RequestMapping("/api")
public class SituationResource {

    private final Logger log = LoggerFactory.getLogger(SituationResource.class);

    private static final String ENTITY_NAME = "situation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationService situationService;

    public SituationResource(SituationService situationService) {
        this.situationService = situationService;
    }

    /**
     * {@code POST  /situations} : Create a new situation.
     *
     * @param situationDTO the situationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situationDTO, or with status {@code 400 (Bad Request)} if the situation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situations")
    public ResponseEntity<SituationDTO> createSituation(@Valid @RequestBody SituationDTO situationDTO) throws URISyntaxException {
        log.debug("REST request to save Situation : {}", situationDTO);
        if (situationDTO.getId() != null) {
            throw new BadRequestAlertException("A new situation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituationDTO result = situationService.save(situationDTO);
        return ResponseEntity.created(new URI("/api/situations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situations} : Updates an existing situation.
     *
     * @param situationDTO the situationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationDTO,
     * or with status {@code 400 (Bad Request)} if the situationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situations")
    public ResponseEntity<SituationDTO> updateSituation(@Valid @RequestBody SituationDTO situationDTO) throws URISyntaxException {
        log.debug("REST request to update Situation : {}", situationDTO);
        if (situationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SituationDTO result = situationService.save(situationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /situations} : get all the situations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situations in body.
     */
    @GetMapping("/situations")
    public List<SituationDTO> getAllSituations() {
        log.debug("REST request to get all Situations");
        return situationService.findAll();
    }

    /**
     * {@code GET  /situations/:id} : get the "id" situation.
     *
     * @param id the id of the situationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situations/{id}")
    public ResponseEntity<SituationDTO> getSituation(@PathVariable Long id) {
        log.debug("REST request to get Situation : {}", id);
        Optional<SituationDTO> situationDTO = situationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situationDTO);
    }

    /**
     * {@code DELETE  /situations/:id} : delete the "id" situation.
     *
     * @param id the id of the situationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situations/{id}")
    public ResponseEntity<Void> deleteSituation(@PathVariable Long id) {
        log.debug("REST request to delete Situation : {}", id);
        situationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
