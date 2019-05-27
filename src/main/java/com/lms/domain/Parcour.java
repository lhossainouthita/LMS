package com.lms.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Parcour.
 */
@Entity
@Table(name = "parcour")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parcour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titre_parcour", nullable = false)
    private String titreParcour;

    @NotNull
    @Column(name = "niveau_parcour", nullable = false)
    private Integer niveauParcour;

    @OneToMany(mappedBy = "parcour")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exercice> exercices = new HashSet<>();

    @OneToMany(mappedBy = "parcour")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cours> cours = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("parcours")
    private User tuteur;

    @ManyToOne
    @JsonIgnoreProperties("parcours")
    private ModulePedagogique modulePedagogique;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreParcour() {
        return titreParcour;
    }

    public Parcour titreParcour(String titreParcour) {
        this.titreParcour = titreParcour;
        return this;
    }

    public void setTitreParcour(String titreParcour) {
        this.titreParcour = titreParcour;
    }

    public Integer getNiveauParcour() {
        return niveauParcour;
    }

    public Parcour niveauParcour(Integer niveauParcour) {
        this.niveauParcour = niveauParcour;
        return this;
    }

    public void setNiveauParcour(Integer niveauParcour) {
        this.niveauParcour = niveauParcour;
    }

    public Set<Exercice> getExercices() {
        return exercices;
    }

    public Parcour exercices(Set<Exercice> exercices) {
        this.exercices = exercices;
        return this;
    }

    public Parcour addExercice(Exercice exercice) {
        this.exercices.add(exercice);
        exercice.setParcour(this);
        return this;
    }

    public Parcour removeExercice(Exercice exercice) {
        this.exercices.remove(exercice);
        exercice.setParcour(null);
        return this;
    }

    public void setExercices(Set<Exercice> exercices) {
        this.exercices = exercices;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public Parcour cours(Set<Cours> cours) {
        this.cours = cours;
        return this;
    }

    public Parcour addCours(Cours cours) {
        this.cours.add(cours);
        cours.setParcour(this);
        return this;
    }

    public Parcour removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setParcour(null);
        return this;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }

    public User getTuteur() {
        return tuteur;
    }

    public Parcour tuteur(User user) {
        this.tuteur = user;
        return this;
    }

    public void setTuteur(User user) {
        this.tuteur = user;
    }

    public ModulePedagogique getModulePedagogique() {
        return modulePedagogique;
    }

    public Parcour modulePedagogique(ModulePedagogique modulePedagogique) {
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
        if (!(o instanceof Parcour)) {
            return false;
        }
        return id != null && id.equals(((Parcour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Parcour{" +
            "id=" + getId() +
            ", titreParcour='" + getTitreParcour() + "'" +
            ", niveauParcour=" + getNiveauParcour() +
            "}";
    }
}
