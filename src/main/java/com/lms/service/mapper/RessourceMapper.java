package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.RessourceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ressource} and its DTO {@link RessourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExerciceMapper.class, CoursMapper.class})
public interface RessourceMapper extends EntityMapper<RessourceDTO, Ressource> {

    @Mapping(source = "exercice.id", target = "exerciceId")
    @Mapping(source = "cours.id", target = "coursId")
    RessourceDTO toDto(Ressource ressource);

    @Mapping(source = "exerciceId", target = "exercice")
    @Mapping(source = "coursId", target = "cours")
    Ressource toEntity(RessourceDTO ressourceDTO);

    default Ressource fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ressource ressource = new Ressource();
        ressource.setId(id);
        return ressource;
    }
}
