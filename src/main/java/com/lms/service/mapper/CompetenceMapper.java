package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.CompetenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competence} and its DTO {@link CompetenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModulePedagogiqueMapper.class})
public interface CompetenceMapper extends EntityMapper<CompetenceDTO, Competence> {

    @Mapping(source = "modulePedagogique.id", target = "modulePedagogiqueId")
    CompetenceDTO toDto(Competence competence);

    @Mapping(source = "modulePedagogiqueId", target = "modulePedagogique")
    Competence toEntity(CompetenceDTO competenceDTO);

    default Competence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competence competence = new Competence();
        competence.setId(id);
        return competence;
    }
}
