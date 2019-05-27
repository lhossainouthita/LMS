package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Devoir} entity.
 */
public class DevoirDTO implements Serializable {

    private Long id;

    @NotNull
    private String titreDevoir;

    @NotNull
    private String cheminDevoir;


    private Long modulePedagogiqueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreDevoir() {
        return titreDevoir;
    }

    public void setTitreDevoir(String titreDevoir) {
        this.titreDevoir = titreDevoir;
    }

    public String getCheminDevoir() {
        return cheminDevoir;
    }

    public void setCheminDevoir(String cheminDevoir) {
        this.cheminDevoir = cheminDevoir;
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

        DevoirDTO devoirDTO = (DevoirDTO) o;
        if (devoirDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devoirDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DevoirDTO{" +
            "id=" + getId() +
            ", titreDevoir='" + getTitreDevoir() + "'" +
            ", cheminDevoir='" + getCheminDevoir() + "'" +
            ", modulePedagogique=" + getModulePedagogiqueId() +
            "}";
    }
}
