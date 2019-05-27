package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.ParcourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parcour} and its DTO {@link ParcourDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ModulePedagogiqueMapper.class})
public interface ParcourMapper extends EntityMapper<ParcourDTO, Parcour> {

    @Mapping(source = "tuteur.id", target = "tuteurId")
    @Mapping(source = "tuteur.firstName", target = "tuteurFirstName")
    @Mapping(source = "modulePedagogique.id", target = "modulePedagogiqueId")
    @Mapping(source = "modulePedagogique.intituleModule", target = "modulePedagogiqueIntituleModule")
    ParcourDTO toDto(Parcour parcour);

    @Mapping(target = "exercices", ignore = true)
    @Mapping(target = "cours", ignore = true)
    @Mapping(source = "tuteurId", target = "tuteur")
    @Mapping(source = "modulePedagogiqueId", target = "modulePedagogique")
    Parcour toEntity(ParcourDTO parcourDTO);

    default Parcour fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parcour parcour = new Parcour();
        parcour.setId(id);
        return parcour;
    }
}
