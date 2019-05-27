package com.lms.service;

import com.lms.domain.Exercice;
import com.lms.repository.ExerciceRepository;
import com.lms.service.dto.ExerciceDTO;
import com.lms.service.mapper.ExerciceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exercice}.
 */
@Service
@Transactional
public class ExerciceService {

    private final Logger log = LoggerFactory.getLogger(ExerciceService.class);

    private final ExerciceRepository exerciceRepository;

    private final ExerciceMapper exerciceMapper;

    public ExerciceService(ExerciceRepository exerciceRepository, ExerciceMapper exerciceMapper) {
        this.exerciceRepository = exerciceRepository;
        this.exerciceMapper = exerciceMapper;
    }

    /**
     * Save a exercice.
     *
     * @param exerciceDTO the entity to save.
     * @return the persisted entity.
     */
    public ExerciceDTO save(ExerciceDTO exerciceDTO) {
        log.debug("Request to save Exercice : {}", exerciceDTO);
        Exercice exercice = exerciceMapper.toEntity(exerciceDTO);
        exercice = exerciceRepository.save(exercice);
        return exerciceMapper.toDto(exercice);
    }

    /**
     * Get all the exercices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ExerciceDTO> findAll() {
        log.debug("Request to get all Exercices");
        return exerciceRepository.findAll().stream()
            .map(exerciceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one exercice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExerciceDTO> findOne(Long id) {
        log.debug("Request to get Exercice : {}", id);
        return exerciceRepository.findById(id)
            .map(exerciceMapper::toDto);
    }

    /**
     * Delete the exercice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Exercice : {}", id);
        exerciceRepository.deleteById(id);
    }
}
