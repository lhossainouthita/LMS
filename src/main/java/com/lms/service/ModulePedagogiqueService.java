package com.lms.service;

import com.lms.domain.ModulePedagogique;
import com.lms.repository.ModulePedagogiqueRepository;
import com.lms.service.dto.ModulePedagogiqueDTO;
import com.lms.service.mapper.ModulePedagogiqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ModulePedagogique}.
 */
@Service
@Transactional
public class ModulePedagogiqueService {

    private final Logger log = LoggerFactory.getLogger(ModulePedagogiqueService.class);

    private final ModulePedagogiqueRepository modulePedagogiqueRepository;

    private final ModulePedagogiqueMapper modulePedagogiqueMapper;

    public ModulePedagogiqueService(ModulePedagogiqueRepository modulePedagogiqueRepository, ModulePedagogiqueMapper modulePedagogiqueMapper) {
        this.modulePedagogiqueRepository = modulePedagogiqueRepository;
        this.modulePedagogiqueMapper = modulePedagogiqueMapper;
    }

    /**
     * Save a modulePedagogique.
     *
     * @param modulePedagogiqueDTO the entity to save.
     * @return the persisted entity.
     */
    public ModulePedagogiqueDTO save(ModulePedagogiqueDTO modulePedagogiqueDTO) {
        log.debug("Request to save ModulePedagogique : {}", modulePedagogiqueDTO);
        ModulePedagogique modulePedagogique = modulePedagogiqueMapper.toEntity(modulePedagogiqueDTO);
        modulePedagogique = modulePedagogiqueRepository.save(modulePedagogique);
        return modulePedagogiqueMapper.toDto(modulePedagogique);
    }

    /**
     * Get all the modulePedagogiques.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ModulePedagogiqueDTO> findAll() {
        log.debug("Request to get all ModulePedagogiques");
        return modulePedagogiqueRepository.findAll().stream()
            .map(modulePedagogiqueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one modulePedagogique by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModulePedagogiqueDTO> findOne(Long id) {
        log.debug("Request to get ModulePedagogique : {}", id);
        return modulePedagogiqueRepository.findById(id)
            .map(modulePedagogiqueMapper::toDto);
    }

    /**
     * Delete the modulePedagogique by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ModulePedagogique : {}", id);
        modulePedagogiqueRepository.deleteById(id);
    }
}
