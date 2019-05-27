package com.lms.web.rest;

import com.lms.service.ParcourService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.ParcourDTO;

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
 * REST controller for managing {@link com.lms.domain.Parcour}.
 */
@RestController
@RequestMapping("/api")
public class ParcourResource {

    private final Logger log = LoggerFactory.getLogger(ParcourResource.class);

    private static final String ENTITY_NAME = "parcour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcourService parcourService;

    public ParcourResource(ParcourService parcourService) {
        this.parcourService = parcourService;
    }

    /**
     * {@code POST  /parcours} : Create a new parcour.
     *
     * @param parcourDTO the parcourDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcourDTO, or with status {@code 400 (Bad Request)} if the parcour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parcours")
    public ResponseEntity<ParcourDTO> createParcour(@Valid @RequestBody ParcourDTO parcourDTO) throws URISyntaxException {
        log.debug("REST request to save Parcour : {}", parcourDTO);
        if (parcourDTO.getId() != null) {
            throw new BadRequestAlertException("A new parcour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParcourDTO result = parcourService.save(parcourDTO);
        return ResponseEntity.created(new URI("/api/parcours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parcours} : Updates an existing parcour.
     *
     * @param parcourDTO the parcourDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parcourDTO,
     * or with status {@code 400 (Bad Request)} if the parcourDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parcourDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parcours")
    public ResponseEntity<ParcourDTO> updateParcour(@Valid @RequestBody ParcourDTO parcourDTO) throws URISyntaxException {
        log.debug("REST request to update Parcour : {}", parcourDTO);
        if (parcourDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParcourDTO result = parcourService.save(parcourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parcourDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parcours} : get all the parcours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcours in body.
     */
    @GetMapping("/parcours")
    public List<ParcourDTO> getAllParcours() {
        log.debug("REST request to get all Parcours");
        return parcourService.findAll();
    }

    /**
     * {@code GET  /parcours/:id} : get the "id" parcour.
     *
     * @param id the id of the parcourDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcourDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parcours/{id}")
    public ResponseEntity<ParcourDTO> getParcour(@PathVariable Long id) {
        log.debug("REST request to get Parcour : {}", id);
        Optional<ParcourDTO> parcourDTO = parcourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parcourDTO);
    }

    /**
     * {@code DELETE  /parcours/:id} : delete the "id" parcour.
     *
     * @param id the id of the parcourDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parcours/{id}")
    public ResponseEntity<Void> deleteParcour(@PathVariable Long id) {
        log.debug("REST request to delete Parcour : {}", id);
        parcourService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
