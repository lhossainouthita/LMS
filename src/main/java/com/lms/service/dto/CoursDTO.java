package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Cours} entity.
 */
public class CoursDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreCours;

    @NotNull
    private String contenuCours;


    private Long parcourId;

    private String parcourTitreParcour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreCours() {
        return titreCours;
    }

    public void setTitreCours(String titreCours) {
        this.titreCours = titreCours;
    }

    public String getContenuCours() {
        return contenuCours;
    }

    public void setContenuCours(String contenuCours) {
        this.contenuCours = contenuCours;
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

        CoursDTO coursDTO = (CoursDTO) o;
        if (coursDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coursDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoursDTO{" +
            "id=" + getId() +
            ", titreCours='" + getTitreCours() + "'" +
            ", contenuCours='" + getContenuCours() + "'" +
            ", parcour=" + getParcourId() +
            ", parcour='" + getParcourTitreParcour() + "'" +
            "}";
    }
}
