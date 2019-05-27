package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.PeriodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Periode} and its DTO {@link PeriodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {SujetMapper.class})
public interface PeriodeMapper extends EntityMapper<PeriodeDTO, Periode> {

    @Mapping(source = "sujet.id", target = "sujetId")
    PeriodeDTO toDto(Periode periode);

    @Mapping(source = "sujetId", target = "sujet")
    Periode toEntity(PeriodeDTO periodeDTO);

    default Periode fromId(Long id) {
        if (id == null) {
            return null;
        }
        Periode periode = new Periode();
        periode.setId(id);
        return periode;
    }
}
