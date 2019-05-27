package com.lms.service;

import com.lms.domain.Reponse;
import com.lms.repository.ReponseRepository;
import com.lms.service.dto.ReponseDTO;
import com.lms.service.mapper.ReponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Reponse}.
 */
@Service
@Transactional
public class ReponseService {

    private final Logger log = LoggerFactory.getLogger(ReponseService.class);

    private final ReponseRepository reponseRepository;

    private final ReponseMapper reponseMapper;

    public ReponseService(ReponseRepository reponseRepository, ReponseMapper reponseMapper) {
        this.reponseRepository = reponseRepository;
        this.reponseMapper = reponseMapper;
    }

    /**
     * Save a reponse.
     *
     * @param reponseDTO the entity to save.
     * @return the persisted entity.
     */
    public ReponseDTO save(ReponseDTO reponseDTO) {
        log.debug("Request to save Reponse : {}", reponseDTO);
        Reponse reponse = reponseMapper.toEntity(reponseDTO);
        reponse = reponseRepository.save(reponse);
        return reponseMapper.toDto(reponse);
    }

    /**
     * Get all the reponses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReponseDTO> findAll() {
        log.debug("Request to get all Reponses");
        return reponseRepository.findAll().stream()
            .map(reponseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one reponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReponseDTO> findOne(Long id) {
        log.debug("Request to get Reponse : {}", id);
        return reponseRepository.findById(id)
            .map(reponseMapper::toDto);
    }

    /**
     * Delete the reponse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reponse : {}", id);
        reponseRepository.deleteById(id);
    }
}
