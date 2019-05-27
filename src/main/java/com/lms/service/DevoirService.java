package com.lms.service;

import com.lms.domain.Devoir;
import com.lms.repository.DevoirRepository;
import com.lms.service.dto.DevoirDTO;
import com.lms.service.mapper.DevoirMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Devoir}.
 */
@Service
@Transactional
public class DevoirService {

    private final Logger log = LoggerFactory.getLogger(DevoirService.class);

    private final DevoirRepository devoirRepository;

    private final DevoirMapper devoirMapper;

    public DevoirService(DevoirRepository devoirRepository, DevoirMapper devoirMapper) {
        this.devoirRepository = devoirRepository;
        this.devoirMapper = devoirMapper;
    }

    /**
     * Save a devoir.
     *
     * @param devoirDTO the entity to save.
     * @return the persisted entity.
     */
    public DevoirDTO save(DevoirDTO devoirDTO) {
        log.debug("Request to save Devoir : {}", devoirDTO);
        Devoir devoir = devoirMapper.toEntity(devoirDTO);
        devoir = devoirRepository.save(devoir);
        return devoirMapper.toDto(devoir);
    }

    /**
     * Get all the devoirs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DevoirDTO> findAll() {
        log.debug("Request to get all Devoirs");
        return devoirRepository.findAll().stream()
            .map(devoirMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one devoir by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DevoirDTO> findOne(Long id) {
        log.debug("Request to get Devoir : {}", id);
        return devoirRepository.findById(id)
            .map(devoirMapper::toDto);
    }

    /**
     * Delete the devoir by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Devoir : {}", id);
        devoirRepository.deleteById(id);
    }
}
