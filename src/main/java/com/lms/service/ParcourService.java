package com.lms.service;

import com.lms.domain.Parcour;
import com.lms.repository.ParcourRepository;
import com.lms.service.dto.ParcourDTO;
import com.lms.service.mapper.ParcourMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Parcour}.
 */
@Service
@Transactional
public class ParcourService {

    private final Logger log = LoggerFactory.getLogger(ParcourService.class);

    private final ParcourRepository parcourRepository;

    private final ParcourMapper parcourMapper;

    public ParcourService(ParcourRepository parcourRepository, ParcourMapper parcourMapper) {
        this.parcourRepository = parcourRepository;
        this.parcourMapper = parcourMapper;
    }

    /**
     * Save a parcour.
     *
     * @param parcourDTO the entity to save.
     * @return the persisted entity.
     */
    public ParcourDTO save(ParcourDTO parcourDTO) {
        log.debug("Request to save Parcour : {}", parcourDTO);
        Parcour parcour = parcourMapper.toEntity(parcourDTO);
        parcour = parcourRepository.save(parcour);
        return parcourMapper.toDto(parcour);
    }

    /**
     * Get all the parcours.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ParcourDTO> findAll() {
        log.debug("Request to get all Parcours");
        return parcourRepository.findAll().stream()
            .map(parcourMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one parcour by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParcourDTO> findOne(Long id) {
        log.debug("Request to get Parcour : {}", id);
        return parcourRepository.findById(id)
            .map(parcourMapper::toDto);
    }

    /**
     * Delete the parcour by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Parcour : {}", id);
        parcourRepository.deleteById(id);
    }
}
