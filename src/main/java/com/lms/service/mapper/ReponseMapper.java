package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.ReponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reponse} and its DTO {@link ReponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ReponseMapper extends EntityMapper<ReponseDTO, Reponse> {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.contenuQuestion", target = "questionContenuQuestion")
    ReponseDTO toDto(Reponse reponse);

    @Mapping(source = "questionId", target = "question")
    Reponse toEntity(ReponseDTO reponseDTO);

    default Reponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reponse reponse = new Reponse();
        reponse.setId(id);
        return reponse;
    }
}
