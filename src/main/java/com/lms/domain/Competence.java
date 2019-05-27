package com.lms.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Competence.
 */
@Entity
@Table(name = "competence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Competence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_competence", nullable = false)
    private String codeCompetence;

    @NotNull
    @Column(name = "intitule_competence", nullable = false)
    private String intituleCompetence;

    @Column(name = "description_competence")
    private String descriptionCompetence;

    @ManyToOne
    @JsonIgnoreProperties("competences")
    private ModulePedagogique modulePedagogique;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCompetence() {
        return codeCompetence;
    }

    public Competence codeCompetence(String codeCompetence) {
        this.codeCompetence = codeCompetence;
        return this;
    }

    public void setCodeCompetence(String codeCompetence) {
        this.codeCompetence = codeCompetence;
    }

    public String getIntituleCompetence() {
        return intituleCompetence;
    }

    public Competence intituleCompetence(String intituleCompetence) {
        this.intituleCompetence = intituleCompetence;
        return this;
    }

    public void setIntituleCompetence(String intituleCompetence) {
        this.intituleCompetence = intituleCompetence;
    }

    public String getDescriptionCompetence() {
        return descriptionCompetence;
    }

    public Competence descriptionCompetence(String descriptionCompetence) {
        this.descriptionCompetence = descriptionCompetence;
        return this;
    }

    public void setDescriptionCompetence(String descriptionCompetence) {
        this.descriptionCompetence = descriptionCompetence;
    }

    public ModulePedagogique getModulePedagogique() {
        return modulePedagogique;
    }

    public Competence modulePedagogique(ModulePedagogique modulePedagogique) {
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
        if (!(o instanceof Competence)) {
            return false;
        }
        return id != null && id.equals(((Competence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Competence{" +
            "id=" + getId() +
            ", codeCompetence='" + getCodeCompetence() + "'" +
            ", intituleCompetence='" + getIntituleCompetence() + "'" +
            ", descriptionCompetence='" + getDescriptionCompetence() + "'" +
            "}";
    }
}
