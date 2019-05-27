package com.lms.web.rest;

import com.lms.service.PeriodeService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.PeriodeDTO;

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
 * REST controller for managing {@link com.lms.domain.Periode}.
 */
@RestController
@RequestMapping("/api")
public class PeriodeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodeResource.class);

    private static final String ENTITY_NAME = "periode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodeService periodeService;

    public PeriodeResource(PeriodeService periodeService) {
        this.periodeService = periodeService;
    }

    /**
     * {@code POST  /periodes} : Create a new periode.
     *
     * @param periodeDTO the periodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodeDTO, or with status {@code 400 (Bad Request)} if the periode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodes")
    public ResponseEntity<PeriodeDTO> createPeriode(@Valid @RequestBody PeriodeDTO periodeDTO) throws URISyntaxException {
        log.debug("REST request to save Periode : {}", periodeDTO);
        if (periodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new periode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodeDTO result = periodeService.save(periodeDTO);
        return ResponseEntity.created(new URI("/api/periodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodes} : Updates an existing periode.
     *
     * @param periodeDTO the periodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodeDTO,
     * or with status {@code 400 (Bad Request)} if the periodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodes")
    public ResponseEntity<PeriodeDTO> updatePeriode(@Valid @RequestBody PeriodeDTO periodeDTO) throws URISyntaxException {
        log.debug("REST request to update Periode : {}", periodeDTO);
        if (periodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodeDTO result = periodeService.save(periodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /periodes} : get all the periodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodes in body.
     */
    @GetMapping("/periodes")
    public List<PeriodeDTO> getAllPeriodes() {
        log.debug("REST request to get all Periodes");
        return periodeService.findAll();
    }

    /**
     * {@code GET  /periodes/:id} : get the "id" periode.
     *
     * @param id the id of the periodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodes/{id}")
    public ResponseEntity<PeriodeDTO> getPeriode(@PathVariable Long id) {
        log.debug("REST request to get Periode : {}", id);
        Optional<PeriodeDTO> periodeDTO = periodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodeDTO);
    }

    /**
     * {@code DELETE  /periodes/:id} : delete the "id" periode.
     *
     * @param id the id of the periodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodes/{id}")
    public ResponseEntity<Void> deletePeriode(@PathVariable Long id) {
        log.debug("REST request to delete Periode : {}", id);
        periodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
