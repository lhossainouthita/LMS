package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.lms.domain.enumeration.TypeRessource;

/**
 * A DTO for the {@link com.lms.domain.Ressource} entity.
 */
public class RessourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreRessource;

    @NotNull
    private String cheminRessource;

    private TypeRessource typeRessource;


    private Long exerciceId;

    private Long coursId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreRessource() {
        return titreRessource;
    }

    public void setTitreRessource(String titreRessource) {
        this.titreRessource = titreRessource;
    }

    public String getCheminRessource() {
        return cheminRessource;
    }

    public void setCheminRessource(String cheminRessource) {
        this.cheminRessource = cheminRessource;
    }

    public TypeRessource getTypeRessource() {
        return typeRessource;
    }

    public void setTypeRessource(TypeRessource typeRessource) {
        this.typeRessource = typeRessource;
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

        RessourceDTO ressourceDTO = (RessourceDTO) o;
        if (ressourceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ressourceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RessourceDTO{" +
            "id=" + getId() +
            ", titreRessource='" + getTitreRessource() + "'" +
            ", cheminRessource='" + getCheminRessource() + "'" +
            ", typeRessource='" + getTypeRessource() + "'" +
            ", exercice=" + getExerciceId() +
            ", cours=" + getCoursId() +
            "}";
    }
}
