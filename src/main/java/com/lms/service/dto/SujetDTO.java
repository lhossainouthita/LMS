package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Sujet} entity.
 */
public class SujetDTO implements Serializable {

    private Long id;

    @NotNull
    private String intituleSujet;

    private String descriptionSujet;


    private Long modulePedagogiqueId;

    private Long competenceId;

    private String competenceIntituleCompetence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleSujet() {
        return intituleSujet;
    }

    public void setIntituleSujet(String intituleSujet) {
        this.intituleSujet = intituleSujet;
    }

    public String getDescriptionSujet() {
        return descriptionSujet;
    }

    public void setDescriptionSujet(String descriptionSujet) {
        this.descriptionSujet = descriptionSujet;
    }

    public Long getModulePedagogiqueId() {
        return modulePedagogiqueId;
    }

    public void setModulePedagogiqueId(Long modulePedagogiqueId) {
        this.modulePedagogiqueId = modulePedagogiqueId;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    public String getCompetenceIntituleCompetence() {
        return competenceIntituleCompetence;
    }

    public void setCompetenceIntituleCompetence(String competenceIntituleCompetence) {
        this.competenceIntituleCompetence = competenceIntituleCompetence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SujetDTO sujetDTO = (SujetDTO) o;
        if (sujetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sujetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SujetDTO{" +
            "id=" + getId() +
            ", intituleSujet='" + getIntituleSujet() + "'" +
            ", descriptionSujet='" + getDescriptionSujet() + "'" +
            ", modulePedagogique=" + getModulePedagogiqueId() +
            ", competence=" + getCompetenceId() +
            ", competence='" + getCompetenceIntituleCompetence() + "'" +
            "}";
    }
}
