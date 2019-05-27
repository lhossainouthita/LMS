package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.SituationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Situation} and its DTO {@link SituationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExerciceMapper.class, CoursMapper.class})
public interface SituationMapper extends EntityMapper<SituationDTO, Situation> {

    @Mapping(source = "exercice.id", target = "exerciceId")
    @Mapping(source = "cours.id", target = "coursId")
    SituationDTO toDto(Situation situation);

    @Mapping(source = "exerciceId", target = "exercice")
    @Mapping(source = "coursId", target = "cours")
    @Mapping(target = "questions", ignore = true)
    Situation toEntity(SituationDTO situationDTO);

    default Situation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Situation situation = new Situation();
        situation.setId(id);
        return situation;
    }
}
