package com.lms.service.mapper;

import com.lms.domain.*;
import com.lms.service.dto.ModulePedagogiqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModulePedagogique} and its DTO {@link ModulePedagogiqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ModulePedagogiqueMapper extends EntityMapper<ModulePedagogiqueDTO, ModulePedagogique> {

    @Mapping(source = "admin.id", target = "adminId")
    @Mapping(source = "admin.firstName", target = "adminFirstName")
    ModulePedagogiqueDTO toDto(ModulePedagogique modulePedagogique);

    @Mapping(target = "parcours", ignore = true)
    @Mapping(target = "sujets", ignore = true)
    @Mapping(target = "competences", ignore = true)
    @Mapping(target = "devoirs", ignore = true)
    @Mapping(source = "adminId", target = "admin")
    ModulePedagogique toEntity(ModulePedagogiqueDTO modulePedagogiqueDTO);

    default ModulePedagogique fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModulePedagogique modulePedagogique = new ModulePedagogique();
        modulePedagogique.setId(id);
        return modulePedagogique;
    }
}
