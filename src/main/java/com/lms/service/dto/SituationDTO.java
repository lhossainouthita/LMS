package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Situation} entity.
 */
public class SituationDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreSituation;

    @NotNull
    private String contenuSituation;


    private Long exerciceId;

    private Long coursId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreSituation() {
        return titreSituation;
    }

    public void setTitreSituation(String titreSituation) {
        this.titreSituation = titreSituation;
    }

    public String getContenuSituation() {
        return contenuSituation;
    }

    public void setContenuSituation(String contenuSituation) {
        this.contenuSituation = contenuSituation;
    }

    public Long getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(Long exerciceId) {
        this.exerciceId = exerciceId;
    }

    public Long getCoursId() {
        return coursId;
    }

    public void setCoursId(Long coursId) {
        this.coursId = coursId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SituationDTO situationDTO = (SituationDTO) o;
        if (situationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), situationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SituationDTO{" +
            "id=" + getId() +
            ", titreSituation='" + getTitreSituation() + "'" +
            ", contenuSituation='" + getContenuSituation() + "'" +
            ", exercice=" + getExerciceId() +
            ", cours=" + getCoursId() +
            "}";
    }
}
