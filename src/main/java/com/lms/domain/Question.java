package com.lms.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "num_question", nullable = false)
    private String numQuestion;

    @NotNull
    @Column(name = "contenu_question", nullable = false)
    private String contenuQuestion;

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private Situation situation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumQuestion() {
        return numQuestion;
    }

    public Question numQuestion(String numQuestion) {
        this.numQuestion = numQuestion;
        return this;
    }

    public void setNumQuestion(String numQuestion) {
        this.numQuestion = numQuestion;
    }

    public String getContenuQuestion() {
        return contenuQuestion;
    }

    public Question contenuQuestion(String contenuQuestion) {
        this.contenuQuestion = contenuQuestion;
        return this;
    }

    public void setContenuQuestion(String contenuQuestion) {
        this.contenuQuestion = contenuQuestion;
    }

    public Situation getSituation() {
        return situation;
    }

    public Question situation(Situation situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", numQuestion='" + getNumQuestion() + "'" +
            ", contenuQuestion='" + getContenuQuestion() + "'" +
            "}";
    }
}
