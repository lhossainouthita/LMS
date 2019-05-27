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
 * A Exercice.
 */
@Entity
@Table(name = "exercice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exercice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titre_exercice", nullable = false)
    private String titreExercice;

    @NotNull
    @Column(name = "contenu_exercice", nullable = false)
    private String contenuExercice;

    @OneToMany(mappedBy = "exercice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Situation> questions = new HashSet<>();

    @OneToMany(mappedBy = "exercice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ressource> ressources = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("exercices")
    private Parcour parcour;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreExercice() {
        return titreExercice;
    }

    public Exercice titreExercice(String titreExercice) {
        this.titreExercice = titreExercice;
        return this;
    }

    public void setTitreExercice(String titreExercice) {
        this.titreExercice = titreExercice;
    }

    public String getContenuExercice() {
        return contenuExercice;
    }

    public Exercice contenuExercice(String contenuExercice) {
        this.contenuExercice = contenuExercice;
        return this;
    }

    public void setContenuExercice(String contenuExercice) {
        this.contenuExercice = contenuExercice;
    }

    public Set<Situation> getQuestions() {
        return questions;
    }

    public Exercice questions(Set<Situation> situations) {
        this.questions = situations;
        return this;
    }

    public Exercice addQuestion(Situation situation) {
        this.questions.add(situation);
        situation.setExercice(this);
        return this;
    }

    public Exercice removeQuestion(Situation situation) {
        this.questions.remove(situation);
        situation.setExercice(null);
        return this;
    }

    public void setQuestions(Set<Situation> situations) {
        this.questions = situations;
    }

    public Set<Ressource> getRessources() {
        return ressources;
    }

    public Exercice ressources(Set<Ressource> ressources) {
        this.ressources = ressources;
        return this;
    }

    public Exercice addRessource(Ressource ressource) {
        this.ressources.add(ressource);
        ressource.setExercice(this);
        return this;
    }

    public Exercice removeRessource(Ressource ressource) {
        this.ressources.remove(ressource);
        ressource.setExercice(null);
        return this;
    }

    public void setRessources(Set<Ressource> ressources) {
        this.ressources = ressources;
    }

    public Parcour getParcour() {
        return parcour;
    }

    public Exercice parcour(Parcour parcour) {
        this.parcour = parcour;
        return this;
    }

    public void setParcour(Parcour parcour) {
        this.parcour = parcour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exercice)) {
            return false;
        }
        return id != null && id.equals(((Exercice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Exercice{" +
            "id=" + getId() +
            ", titreExercice='" + getTitreExercice() + "'" +
            ", contenuExercice='" + getContenuExercice() + "'" +
            "}";
    }
}
