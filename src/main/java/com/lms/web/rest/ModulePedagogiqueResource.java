package com.lms.web.rest;

import com.lms.service.ModulePedagogiqueService;
import com.lms.web.rest.errors.BadRequestAlertException;
import com.lms.service.dto.ModulePedagogiqueDTO;

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
 * REST controller for managing {@link com.lms.domain.ModulePedagogique}.
 */
@RestController
@RequestMapping("/api")
public class ModulePedagogiqueResource {

    private final Logger log = LoggerFactory.getLogger(ModulePedagogiqueResource.class);

    private static final String ENTITY_NAME = "modulePedagogique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModulePedagogiqueService modulePedagogiqueService;

    public ModulePedagogiqueResource(ModulePedagogiqueService modulePedagogiqueService) {
        this.modulePedagogiqueService = modulePedagogiqueService;
    }

    /**
     * {@code POST  /module-pedagogiques} : Create a new modulePedagogique.
     *
     * @param modulePedagogiqueDTO the modulePedagogiqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modulePedagogiqueDTO, or with status {@code 400 (Bad Request)} if the modulePedagogique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/module-pedagogiques")
    public ResponseEntity<ModulePedagogiqueDTO> createModulePedagogique(@Valid @RequestBody ModulePedagogiqueDTO modulePedagogiqueDTO) throws URISyntaxException {
        log.debug("REST request to save ModulePedagogique : {}", modulePedagogiqueDTO);
        if (modulePedagogiqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new modulePedagogique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModulePedagogiqueDTO result = modulePedagogiqueService.save(modulePedagogiqueDTO);
        return ResponseEntity.created(new URI("/api/module-pedagogiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /module-pedagogiques} : Updates an existing modulePedagogique.
     *
     * @param modulePedagogiqueDTO the modulePedagogiqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulePedagogiqueDTO,
     * or with status {@code 400 (Bad Request)} if the modulePedagogiqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modulePedagogiqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/module-pedagogiques")
    public ResponseEntity<ModulePedagogiqueDTO> updateModulePedagogique(@Valid @RequestBody ModulePedagogiqueDTO modulePedagogiqueDTO) throws URISyntaxException {
        log.debug("REST request to update ModulePedagogique : {}", modulePedagogiqueDTO);
        if (modulePedagogiqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModulePedagogiqueDTO result = modulePedagogiqueService.save(modulePedagogiqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulePedagogiqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /module-pedagogiques} : get all the modulePedagogiques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modulePedagogiques in body.
     */
    @GetMapping("/module-pedagogiques")
    public List<ModulePedagogiqueDTO> getAllModulePedagogiques() {
        log.debug("REST request to get all ModulePedagogiques");
        return modulePedagogiqueService.findAll();
    }

    /**
     * {@code GET  /module-pedagogiques/:id} : get the "id" modulePedagogique.
     *
     * @param id the id of the modulePedagogiqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modulePedagogiqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/module-pedagogiques/{id}")
    public ResponseEntity<ModulePedagogiqueDTO> getModulePedagogique(@PathVariable Long id) {
        log.debug("REST request to get ModulePedagogique : {}", id);
        Optional<ModulePedagogiqueDTO> modulePedagogiqueDTO = modulePedagogiqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modulePedagogiqueDTO);
    }

    /**
     * {@code DELETE  /module-pedagogiques/:id} : delete the "id" modulePedagogique.
     *
     * @param id the id of the modulePedagogiqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/module-pedagogiques/{id}")
    public ResponseEntity<Void> deleteModulePedagogique(@PathVariable Long id) {
        log.debug("REST request to delete ModulePedagogique : {}", id);
        modulePedagogiqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
