package com.lms.web.rest;

import com.lms.service.DevoirService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.DevoirDTO;

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
 * REST controller for managing {@link com.lms.domain.Devoir}.
 */
@RestController
@RequestMapping("/api")
public class DevoirResource {

    private final Logger log = LoggerFactory.getLogger(DevoirResource.class);

    private static final String ENTITY_NAME = "devoir";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevoirService devoirService;

    public DevoirResource(DevoirService devoirService) {
        this.devoirService = devoirService;
    }

    /**
     * {@code POST  /devoirs} : Create a new devoir.
     *
     * @param devoirDTO the devoirDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devoirDTO, or with status {@code 400 (Bad Request)} if the devoir has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devoirs")
    public ResponseEntity<DevoirDTO> createDevoir(@Valid @RequestBody DevoirDTO devoirDTO) throws URISyntaxException {
        log.debug("REST request to save Devoir : {}", devoirDTO);
        if (devoirDTO.getId() != null) {
            throw new BadRequestAlertException("A new devoir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DevoirDTO result = devoirService.save(devoirDTO);
        return ResponseEntity.created(new URI("/api/devoirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devoirs} : Updates an existing devoir.
     *
     * @param devoirDTO the devoirDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devoirDTO,
     * or with status {@code 400 (Bad Request)} if the devoirDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devoirDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devoirs")
    public ResponseEntity<DevoirDTO> updateDevoir(@Valid @RequestBody DevoirDTO devoirDTO) throws URISyntaxException {
        log.debug("REST request to update Devoir : {}", devoirDTO);
        if (devoirDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DevoirDTO result = devoirService.save(devoirDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devoirDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /devoirs} : get all the devoirs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devoirs in body.
     */
    @GetMapping("/devoirs")
    public List<DevoirDTO> getAllDevoirs() {
        log.debug("REST request to get all Devoirs");
        return devoirService.findAll();
    }

    /**
     * {@code GET  /devoirs/:id} : get the "id" devoir.
     *
     * @param id the id of the devoirDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devoirDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devoirs/{id}")
    public ResponseEntity<DevoirDTO> getDevoir(@PathVariable Long id) {
        log.debug("REST request to get Devoir : {}", id);
        Optional<DevoirDTO> devoirDTO = devoirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devoirDTO);
    }

    /**
     * {@code DELETE  /devoirs/:id} : delete the "id" devoir.
     *
     * @param id the id of the devoirDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devoirs/{id}")
    public ResponseEntity<Void> deleteDevoir(@PathVariable Long id) {
        log.debug("REST request to delete Devoir : {}", id);
        devoirService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
