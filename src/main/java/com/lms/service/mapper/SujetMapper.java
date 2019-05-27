package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.SujetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sujet} and its DTO {@link SujetDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModulePedagogiqueMapper.class, CompetenceMapper.class})
public interface SujetMapper extends EntityMapper<SujetDTO, Sujet> {

    @Mapping(source = "modulePedagogique.id", target = "modulePedagogiqueId")
    @Mapping(source = "competence.id", target = "competenceId")
    @Mapping(source = "competence.intituleCompetence", target = "competenceIntituleCompetence")
    SujetDTO toDto(Sujet sujet);

    @Mapping(source = "modulePedagogiqueId", target = "modulePedagogique")
    @Mapping(source = "competenceId", target = "competence")
    @Mapping(target = "periodes", ignore = true)
    Sujet toEntity(SujetDTO sujetDTO);

    default Sujet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sujet sujet = new Sujet();
        sujet.setId(id);
        return sujet;
    }
}
