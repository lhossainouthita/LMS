package com.lms.web.rest;

import com.lms.service.RessourceService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.RessourceDTO;

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
 * REST controller for managing {@link com.lms.domain.Ressource}.
 */
@RestController
@RequestMapping("/api")
public class RessourceResource {

    private final Logger log = LoggerFactory.getLogger(RessourceResource.class);

    private static final String ENTITY_NAME = "ressource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RessourceService ressourceService;

    public RessourceResource(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    /**
     * {@code POST  /ressources} : Create a new ressource.
     *
     * @param ressourceDTO the ressourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ressourceDTO, or with status {@code 400 (Bad Request)} if the ressource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ressources")
    public ResponseEntity<RessourceDTO> createRessource(@Valid @RequestBody RessourceDTO ressourceDTO) throws URISyntaxException {
        log.debug("REST request to save Ressource : {}", ressourceDTO);
        if (ressourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ressource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RessourceDTO result = ressourceService.save(ressourceDTO);
        return ResponseEntity.created(new URI("/api/ressources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ressources} : Updates an existing ressource.
     *
     * @param ressourceDTO the ressourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ressourceDTO,
     * or with status {@code 400 (Bad Request)} if the ressourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ressourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ressources")
    public ResponseEntity<RessourceDTO> updateRessource(@Valid @RequestBody RessourceDTO ressourceDTO) throws URISyntaxException {
        log.debug("REST request to update Ressource : {}", ressourceDTO);
        if (ressourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RessourceDTO result = ressourceService.save(ressourceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ressourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ressources} : get all the ressources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ressources in body.
     */
    @GetMapping("/ressources")
    public List<RessourceDTO> getAllRessources() {
        log.debug("REST request to get all Ressources");
        return ressourceService.findAll();
    }

    /**
     * {@code GET  /ressources/:id} : get the "id" ressource.
     *
     * @param id the id of the ressourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ressourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ressources/{id}")
    public ResponseEntity<RessourceDTO> getRessource(@PathVariable Long id) {
        log.debug("REST request to get Ressource : {}", id);
        Optional<RessourceDTO> ressourceDTO = ressourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ressourceDTO);
    }

    /**
     * {@code DELETE  /ressources/:id} : delete the "id" ressource.
     *
     * @param id the id of the ressourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ressources/{id}")
    public ResponseEntity<Void> deleteRessource(@PathVariable Long id) {
        log.debug("REST request to delete Ressource : {}", id);
        ressourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
