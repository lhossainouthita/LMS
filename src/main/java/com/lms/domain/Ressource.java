package com.lms.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.lms.domain.enumeration.TypeRessource;

/**
 * A Ressource.
 */
@Entity
@Table(name = "ressource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ressource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titre_ressource", nullable = false)
    private String titreRessource;

    @NotNull
    @Column(name = "chemin_ressource", nullable = false)
    private String cheminRessource;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_ressource")
    private TypeRessource typeRessource;

    @ManyToOne
    @JsonIgnoreProperties("ressources")
    private Exercice exercice;

    @ManyToOne
    @JsonIgnoreProperties("ressources")
    private Cours cours;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreRessource() {
        return titreRessource;
    }

    public Ressource titreRessource(String titreRessource) {
        this.titreRessource = titreRessource;
        return this;
    }

    public void setTitreRessource(String titreRessource) {
        this.titreRessource = titreRessource;
    }

    public String getCheminRessource() {
        return cheminRessource;
    }

    public Ressource cheminRessource(String cheminRessource) {
        this.cheminRessource = cheminRessource;
        return this;
    }

    public void setCheminRessource(String cheminRessource) {
        this.cheminRessource = cheminRessource;
    }

    public TypeRessource getTypeRessource() {
        return typeRessource;
    }

    public Ressource typeRessource(TypeRessource typeRessource) {
        this.typeRessource = typeRessource;
        return this;
    }

    public void setTypeRessource(TypeRessource typeRessource) {
        this.typeRessource = typeRessource;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public Ressource exercice(Exercice exercice) {
        this.exercice = exercice;
        return this;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Cours getCours() {
        return cours;
    }

    public Ressource cours(Cours cours) {
        this.cours = cours;
        return this;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ressource)) {
            return false;
        }
        return id != null && id.equals(((Ressource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ressource{" +
            "id=" + getId() +
            ", titreRessource='" + getTitreRessource() + "'" +
            ", cheminRessource='" + getCheminRessource() + "'" +
            ", typeRessource='" + getTypeRessource() + "'" +
            "}";
    }
}
