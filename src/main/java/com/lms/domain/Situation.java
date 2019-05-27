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
 * A Situation.
 */
@Entity
@Table(name = "situation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Situation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titre_situation", nullable = false)
    private String titreSituation;

    @NotNull
    @Column(name = "contenu_situation", nullable = false)
    private String contenuSituation;

    @ManyToOne
    @JsonIgnoreProperties("situations")
    private Exercice exercice;

    @ManyToOne
    @JsonIgnoreProperties("situations")
    private Cours cours;

    @OneToMany(mappedBy = "situation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreSituation() {
        return titreSituation;
    }

    public Situation titreSituation(String titreSituation) {
        this.titreSituation = titreSituation;
        return this;
    }

    public void setTitreSituation(String titreSituation) {
        this.titreSituation = titreSituation;
    }

    public String getContenuSituation() {
        return contenuSituation;
    }

    public Situation contenuSituation(String contenuSituation) {
        this.contenuSituation = contenuSituation;
        return this;
    }

    public void setContenuSituation(String contenuSituation) {
        this.contenuSituation = contenuSituation;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public Situation exercice(Exercice exercice) {
        this.exercice = exercice;
        return this;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Cours getCours() {
        return cours;
    }

    public Situation cours(Cours cours) {
        this.cours = cours;
        return this;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Situation questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Situation addQuestion(Question question) {
        this.questions.add(question);
        question.setSituation(this);
        return this;
    }

    public Situation removeQuestion(Question question) {
        this.questions.remove(question);
        question.setSituation(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Situation)) {
            return false;
        }
        return id != null && id.equals(((Situation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Situation{" +
            "id=" + getId() +
            ", titreSituation='" + getTitreSituation() + "'" +
            ", contenuSituation='" + getContenuSituation() + "'" +
            "}";
    }
}
