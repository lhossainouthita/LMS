package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Parcour} entity.
 */
public class ParcourDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreParcour;

    @NotNull
    private Integer niveauParcour;


    private Long tuteurId;

    private String tuteurFirstName;

    private Long modulePedagogiqueId;

    private String modulePedagogiqueIntituleModule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreParcour() {
        return titreParcour;
    }

    public void setTitreParcour(String titreParcour) {
        this.titreParcour = titreParcour;
    }

    public Integer getNiveauParcour() {
        return niveauParcour;
    }

    public void setNiveauParcour(Integer niveauParcour) {
        this.niveauParcour = niveauParcour;
    }

    public Long getTuteurId() {
        return tuteurId;
    }

    public void setTuteurId(Long userId) {
        this.tuteurId = userId;
    }

    public String getTuteurFirstName() {
        return tuteurFirstName;
    }

    public void setTuteurFirstName(String userFirstName) {
        this.tuteurFirstName = userFirstName;
    }

    public Long getModulePedagogiqueId() {
        return modulePedagogiqueId;
    }

    public void setModulePedagogiqueId(Long modulePedagogiqueId) {
        this.modulePedagogiqueId = modulePedagogiqueId;
    }

    public String getModulePedagogiqueIntituleModule() {
        return modulePedagogiqueIntituleModule;
    }

    public void setModulePedagogiqueIntituleModule(String modulePedagogiqueIntituleModule) {
        this.modulePedagogiqueIntituleModule = modulePedagogiqueIntituleModule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParcourDTO parcourDTO = (ParcourDTO) o;
        if (parcourDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parcourDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParcourDTO{" +
            "id=" + getId() +
            ", titreParcour='" + getTitreParcour() + "'" +
            ", niveauParcour=" + getNiveauParcour() +
            ", tuteur=" + getTuteurId() +
            ", tuteur='" + getTuteurFirstName() + "'" +
            ", modulePedagogique=" + getModulePedagogiqueId() +
            ", modulePedagogique='" + getModulePedagogiqueIntituleModule() + "'" +
            "}";
    }
}
