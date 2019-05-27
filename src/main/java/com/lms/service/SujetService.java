package com.lms.service;

import com.lms.domain.Sujet;
import com.lms.repository.SujetRepository;
import com.lms.service.dto.SujetDTO;
import com.lms.service.mapper.SujetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sujet}.
 */
@Service
@Transactional
public class SujetService {

    private final Logger log = LoggerFactory.getLogger(SujetService.class);

    private final SujetRepository sujetRepository;

    private final SujetMapper sujetMapper;

    public SujetService(SujetRepository sujetRepository, SujetMapper sujetMapper) {
        this.sujetRepository = sujetRepository;
        this.sujetMapper = sujetMapper;
    }

    /**
     * Save a sujet.
     *
     * @param sujetDTO the entity to save.
     * @return the persisted entity.
     */
    public SujetDTO save(SujetDTO sujetDTO) {
        log.debug("Request to save Sujet : {}", sujetDTO);
        Sujet sujet = sujetMapper.toEntity(sujetDTO);
        sujet = sujetRepository.save(sujet);
        return sujetMapper.toDto(sujet);
    }

    /**
     * Get all the sujets.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SujetDTO> findAll() {
        log.debug("Request to get all Sujets");
        return sujetRepository.findAll().stream()
            .map(sujetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sujet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SujetDTO> findOne(Long id) {
        log.debug("Request to get Sujet : {}", id);
        return sujetRepository.findById(id)
            .map(sujetMapper::toDto);
    }

    /**
     * Delete the sujet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sujet : {}", id);
        sujetRepository.deleteById(id);
    }
}
