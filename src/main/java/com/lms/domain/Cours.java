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
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titre_cours", nullable = false)
    private String titreCours;

    @NotNull
    @Column(name = "contenu_cours", nullable = false)
    private String contenuCours;

    @OneToMany(mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Situation> questions = new HashSet<>();

    @OneToMany(mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ressource> ressources = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("cours")
    private Parcour parcour;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreCours() {
        return titreCours;
    }

    public Cours titreCours(String titreCours) {
        this.titreCours = titreCours;
        return this;
    }

    public void setTitreCours(String titreCours) {
        this.titreCours = titreCours;
    }

    public String getContenuCours() {
        return contenuCours;
    }

    public Cours contenuCours(String contenuCours) {
        this.contenuCours = contenuCours;
        return this;
    }

    public void setContenuCours(String contenuCours) {
        this.contenuCours = contenuCours;
    }

    public Set<Situation> getQuestions() {
        return questions;
    }

    public Cours questions(Set<Situation> situations) {
        this.questions = situations;
        return this;
    }

    public Cours addQuestion(Situation situation) {
        this.questions.add(situation);
        situation.setCours(this);
        return this;
    }

    public Cours removeQuestion(Situation situation) {
        this.questions.remove(situation);
        situation.setCours(null);
        return this;
    }

    public void setQuestions(Set<Situation> situations) {
        this.questions = situations;
    }

    public Set<Ressource> getRessources() {
        return ressources;
    }

    public Cours ressources(Set<Ressource> ressources) {
        this.ressources = ressources;
        return this;
    }

    public Cours addRessource(Ressource ressource) {
        this.ressources.add(ressource);
        ressource.setCours(this);
        return this;
    }

    public Cours removeRessource(Ressource ressource) {
        this.ressources.remove(ressource);
        ressource.setCours(null);
        return this;
    }

    public void setRessources(Set<Ressource> ressources) {
        this.ressources = ressources;
    }

    public Parcour getParcour() {
        return parcour;
    }

    public Cours parcour(Parcour parcour) {
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
        if (!(o instanceof Cours)) {
            return false;
        }
        return id != null && id.equals(((Cours) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", titreCours='" + getTitreCours() + "'" +
            ", contenuCours='" + getContenuCours() + "'" +
            "}";
    }
}
