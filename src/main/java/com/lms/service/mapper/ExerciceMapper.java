package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.ExerciceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exercice} and its DTO {@link ExerciceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ParcourMapper.class})
public interface ExerciceMapper extends EntityMapper<ExerciceDTO, Exercice> {

    @Mapping(source = "parcour.id", target = "parcourId")
    @Mapping(source = "parcour.titreParcour", target = "parcourTitreParcour")
    ExerciceDTO toDto(Exercice exercice);

    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "ressources", ignore = true)
    @Mapping(source = "parcourId", target = "parcour")
    Exercice toEntity(ExerciceDTO exerciceDTO);

    default Exercice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercice exercice = new Exercice();
        exercice.setId(id);
        return exercice;
    }
}
