package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.CoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cours} and its DTO {@link CoursDTO}.
 */
@Mapper(componentModel = "spring", uses = {ParcourMapper.class})
public interface CoursMapper extends EntityMapper<CoursDTO, Cours> {

    @Mapping(source = "parcour.id", target = "parcourId")
    @Mapping(source = "parcour.titreParcour", target = "parcourTitreParcour")
    CoursDTO toDto(Cours cours);

    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "ressources", ignore = true)
    @Mapping(source = "parcourId", target = "parcour")
    Cours toEntity(CoursDTO coursDTO);

    default Cours fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cours cours = new Cours();
        cours.setId(id);
        return cours;
    }
}
