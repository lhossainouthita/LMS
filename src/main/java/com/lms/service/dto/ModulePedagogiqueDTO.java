package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.ModulePedagogique} entity.
 */
public class ModulePedagogiqueDTO implements Serializable {

    private Long id;

    @NotNull
    private String codeModule;

    @NotNull
    private String intituleModule;

    private String descriptionModule;


    private Long adminId;

    private String adminFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeModule() {
        return codeModule;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    public String getIntituleModule() {
        return intituleModule;
    }

    public void setIntituleModule(String intituleModule) {
        this.intituleModule = intituleModule;
    }

    public String getDescriptionModule() {
        return descriptionModule;
    }

    public void setDescriptionModule(String descriptionModule) {
        this.descriptionModule = descriptionModule;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long userId) {
        this.adminId = userId;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String userFirstName) {
        this.adminFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModulePedagogiqueDTO modulePedagogiqueDTO = (ModulePedagogiqueDTO) o;
        if (modulePedagogiqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modulePedagogiqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModulePedagogiqueDTO{" +
            "id=" + getId() +
            ", codeModule='" + getCodeModule() + "'" +
            ", intituleModule='" + getIntituleModule() + "'" +
            ", descriptionModule='" + getDescriptionModule() + "'" +
            ", admin=" + getAdminId() +
            ", admin='" + getAdminFirstName() + "'" +
            "}";
    }
}
