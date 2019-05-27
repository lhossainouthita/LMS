package com.lms.service;

import com.lms.domain.Situation;
import com.lms.repository.SituationRepository;
import com.lms.service.dto.SituationDTO;
import com.lms.service.mapper.SituationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Situation}.
 */
@Service
@Transactional
public class SituationService {

    private final Logger log = LoggerFactory.getLogger(SituationService.class);

    private final SituationRepository situationRepository;

    private final SituationMapper situationMapper;

    public SituationService(SituationRepository situationRepository, SituationMapper situationMapper) {
        this.situationRepository = situationRepository;
        this.situationMapper = situationMapper;
    }

    /**
     * Save a situation.
     *
     * @param situationDTO the entity to save.
     * @return the persisted entity.
     */
    public SituationDTO save(SituationDTO situationDTO) {
        log.debug("Request to save Situation : {}", situationDTO);
        Situation situation = situationMapper.toEntity(situationDTO);
        situation = situationRepository.save(situation);
        return situationMapper.toDto(situation);
    }

    /**
     * Get all the situations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SituationDTO> findAll() {
        log.debug("Request to get all Situations");
        return situationRepository.findAll().stream()
            .map(situationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one situation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SituationDTO> findOne(Long id) {
        log.debug("Request to get Situation : {}", id);
        return situationRepository.findById(id)
            .map(situationMapper::toDto);
    }

    /**
     * Delete the situation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Situation : {}", id);
        situationRepository.deleteById(id);
    }
}
