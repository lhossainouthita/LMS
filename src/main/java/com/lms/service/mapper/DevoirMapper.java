package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.DevoirDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Devoir} and its DTO {@link DevoirDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModulePedagogiqueMapper.class})
public interface DevoirMapper extends EntityMapper<DevoirDTO, Devoir> {

    @Mapping(source = "modulePedagogique.id", target = "modulePedagogiqueId")
    DevoirDTO toDto(Devoir devoir);

    @Mapping(source = "modulePedagogiqueId", target = "modulePedagogique")
    Devoir toEntity(DevoirDTO devoirDTO);

    default Devoir fromId(Long id) {
        if (id == null) {
            return null;
        }
        Devoir devoir = new Devoir();
        devoir.setId(id);
        return devoir;
    }
}
