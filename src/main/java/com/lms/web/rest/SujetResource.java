package com.lms.web.rest;

import com.lms.service.SujetService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.SujetDTO;

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
 * REST controller for managing {@link com.lms.domain.Sujet}.
 */
@RestController
@RequestMapping("/api")
public class SujetResource {

    private final Logger log = LoggerFactory.getLogger(SujetResource.class);

    private static final String ENTITY_NAME = "sujet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SujetService sujetService;

    public SujetResource(SujetService sujetService) {
        this.sujetService = sujetService;
    }

    /**
     * {@code POST  /sujets} : Create a new sujet.
     *
     * @param sujetDTO the sujetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sujetDTO, or with status {@code 400 (Bad Request)} if the sujet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sujets")
    public ResponseEntity<SujetDTO> createSujet(@Valid @RequestBody SujetDTO sujetDTO) throws URISyntaxException {
        log.debug("REST request to save Sujet : {}", sujetDTO);
        if (sujetDTO.getId() != null) {
            throw new BadRequestAlertException("A new sujet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SujetDTO result = sujetService.save(sujetDTO);
        return ResponseEntity.created(new URI("/api/sujets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sujets} : Updates an existing sujet.
     *
     * @param sujetDTO the sujetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sujetDTO,
     * or with status {@code 400 (Bad Request)} if the sujetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sujetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sujets")
    public ResponseEntity<SujetDTO> updateSujet(@Valid @RequestBody SujetDTO sujetDTO) throws URISyntaxException {
        log.debug("REST request to update Sujet : {}", sujetDTO);
        if (sujetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SujetDTO result = sujetService.save(sujetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sujetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sujets} : get all the sujets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sujets in body.
     */
    @GetMapping("/sujets")
    public List<SujetDTO> getAllSujets() {
        log.debug("REST request to get all Sujets");
        return sujetService.findAll();
    }

    /**
     * {@code GET  /sujets/:id} : get the "id" sujet.
     *
     * @param id the id of the sujetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sujetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sujets/{id}")
    public ResponseEntity<SujetDTO> getSujet(@PathVariable Long id) {
        log.debug("REST request to get Sujet : {}", id);
        Optional<SujetDTO> sujetDTO = sujetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sujetDTO);
    }

    /**
     * {@code DELETE  /sujets/:id} : delete the "id" sujet.
     *
     * @param id the id of the sujetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sujets/{id}")
    public ResponseEntity<Void> deleteSujet(@PathVariable Long id) {
        log.debug("REST request to delete Sujet : {}", id);
        sujetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
