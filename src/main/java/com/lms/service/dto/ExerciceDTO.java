package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Exercice} entity.
 */
public class ExerciceDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreExercice;

    @NotNull
    private String contenuExercice;


    private Long parcourId;

    private String parcourTitreParcour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreExercice() {
        return titreExercice;
    }

    public void setTitreExercice(String titreExercice) {
        this.titreExercice = titreExercice;
    }

    public String getContenuExercice() {
        return contenuExercice;
    }

    public void setContenuExercice(String contenuExercice) {
        this.contenuExercice = contenuExercice;
    }

    public Long getParcourId() {
        return parcourId;
    }

    public void setParcourId(Long parcourId) {
        this.parcourId = parcourId;
    }

    public String getParcourTitreParcour() {
        return parcourTitreParcour;
    }

    public void setParcourTitreParcour(String parcourTitreParcour) {
        this.parcourTitreParcour = parcourTitreParcour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciceDTO exerciceDTO = (ExerciceDTO) o;
        if (exerciceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exerciceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExerciceDTO{" +
            "id=" + getId() +
            ", titreExercice='" + getTitreExercice() + "'" +
            ", contenuExercice='" + getContenuExercice() + "'" +
            ", parcour=" + getParcourId() +
            ", parcour='" + getParcourTitreParcour() + "'" +
            "}";
    }
}
