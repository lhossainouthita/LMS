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
 * A Sujet.
 */
@Entity
@Table(name = "sujet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sujet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "intitule_sujet", nullable = false)
    private String intituleSujet;

    @Column(name = "description_sujet")
    private String descriptionSujet;

    @ManyToOne
    @JsonIgnoreProperties("sujets")
    private ModulePedagogique modulePedagogique;

    @OneToOne
    @JoinColumn(unique = true)
    private Competence competence;

    @OneToMany(mappedBy = "sujet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Periode> periodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleSujet() {
        return intituleSujet;
    }

    public Sujet intituleSujet(String intituleSujet) {
        this.intituleSujet = intituleSujet;
        return this;
    }

    public void setIntituleSujet(String intituleSujet) {
        this.intituleSujet = intituleSujet;
    }

    public String getDescriptionSujet() {
        return descriptionSujet;
    }

    public Sujet descriptionSujet(String descriptionSujet) {
        this.descriptionSujet = descriptionSujet;
        return this;
    }

    public void setDescriptionSujet(String descriptionSujet) {
        this.descriptionSujet = descriptionSujet;
    }

    public ModulePedagogique getModulePedagogique() {
        return modulePedagogique;
    }

    public Sujet modulePedagogique(ModulePedagogique modulePedagogique) {
        this.modulePedagogique = modulePedagogique;
        return this;
    }

    public void setModulePedagogique(ModulePedagogique modulePedagogique) {
        this.modulePedagogique = modulePedagogique;
    }

    public Competence getCompetence() {
        return competence;
    }

    public Sujet competence(Competence competence) {
        this.competence = competence;
        return this;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public Set<Periode> getPeriodes() {
        return periodes;
    }

    public Sujet periodes(Set<Periode> periodes) {
        this.periodes = periodes;
        return this;
    }

    public Sujet addPeriode(Periode periode) {
        this.periodes.add(periode);
        periode.setSujet(this);
        return this;
    }

    public Sujet removePeriode(Periode periode) {
        this.periodes.remove(periode);
        periode.setSujet(null);
        return this;
    }

    public void setPeriodes(Set<Periode> periodes) {
        this.periodes = periodes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sujet)) {
            return false;
        }
        return id != null && id.equals(((Sujet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sujet{" +
            "id=" + getId() +
            ", intituleSujet='" + getIntituleSujet() + "'" +
            ", descriptionSujet='" + getDescriptionSujet() + "'" +
            "}";
    }
}
