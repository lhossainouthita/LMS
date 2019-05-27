package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Competence} entity.
 */
public class CompetenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String codeCompetence;

    @NotNull
    private String intituleCompetence;

    private String descriptionCompetence;


    private Long modulePedagogiqueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCompetence() {
        return codeCompetence;
    }

    public void setCodeCompetence(String codeCompetence) {
        this.codeCompetence = codeCompetence;
    }

    public String getIntituleCompetence() {
        return intituleCompetence;
    }

    public void setIntituleCompetence(String intituleCompetence) {
        this.intituleCompetence = intituleCompetence;
    }

    public String getDescriptionCompetence() {
        return descriptionCompetence;
    }

    public void setDescriptionCompetence(String descriptionCompetence) {
        this.descriptionCompetence = descriptionCompetence;
    }

    public Long getModulePedagogiqueId() {
        return modulePedagogiqueId;
    }

    public void setModulePedagogiqueId(Long modulePedagogiqueId) {
        this.modulePedagogiqueId = modulePedagogiqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetenceDTO competenceDTO = (CompetenceDTO) o;
        if (competenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetenceDTO{" +
            "id=" + getId() +
            ", codeCompetence='" + getCodeCompetence() + "'" +
            ", intituleCompetence='" + getIntituleCompetence() + "'" +
            ", descriptionCompetence='" + getDescriptionCompetence() + "'" +
            ", modulePedagogique=" + getModulePedagogiqueId() +
            "}";
    }
}
