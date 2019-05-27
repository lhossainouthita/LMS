package com.lms.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Devoir.
 */
@Entity
@Table(name = "devoir")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Devoir implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titre_devoir", nullable = false)
    private String titreDevoir;

    @NotNull
    @Column(name = "chemin_devoir", nullable = false)
    private String cheminDevoir;

    @ManyToOne
    @JsonIgnoreProperties("devoirs")
    private ModulePedagogique modulePedagogique;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreDevoir() {
        return titreDevoir;
    }

    public Devoir titreDevoir(String titreDevoir) {
        this.titreDevoir = titreDevoir;
        return this;
    }

    public void setTitreDevoir(String titreDevoir) {
        this.titreDevoir = titreDevoir;
    }

    public String getCheminDevoir() {
        return cheminDevoir;
    }

    public Devoir cheminDevoir(String cheminDevoir) {
        this.cheminDevoir = cheminDevoir;
        return this;
    }

    public void setCheminDevoir(String cheminDevoir) {
        this.cheminDevoir = cheminDevoir;
    }

    public ModulePedagogique getModulePedagogique() {
        return modulePedagogique;
    }

    public Devoir modulePedagogique(ModulePedagogique modulePedagogique) {
        this.modulePedagogique = modulePedagogique;
        return this;
    }

    public void setModulePedagogique(ModulePedagogique modulePedagogique) {
        this.modulePedagogique = modulePedagogique;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Devoir)) {
            return false;
        }
        return id != null && id.equals(((Devoir) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Devoir{" +
            "id=" + getId() +
            ", titreDevoir='" + getTitreDevoir() + "'" +
            ", cheminDevoir='" + getCheminDevoir() + "'" +
            "}";
    }
}
