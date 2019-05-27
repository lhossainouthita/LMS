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
 * A ModulePedagogique.
 */
@Entity
@Table(name = "module_pedagogique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModulePedagogique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_module", nullable = false)
    private String codeModule;

    @NotNull
    @Column(name = "intitule_module", nullable = false)
    private String intituleModule;

    @Column(name = "description_module")
    private String descriptionModule;

    @OneToMany(mappedBy = "modulePedagogique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Parcour> parcours = new HashSet<>();

    @OneToMany(mappedBy = "modulePedagogique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sujet> sujets = new HashSet<>();

    @OneToMany(mappedBy = "modulePedagogique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Competence> competences = new HashSet<>();

    @OneToMany(mappedBy = "modulePedagogique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Devoir> devoirs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("modulePedagogiques")
    private User admin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeModule() {
        return codeModule;
    }

    public ModulePedagogique codeModule(String codeModule) {
        this.codeModule = codeModule;
        return this;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    public String getIntituleModule() {
        return intituleModule;
    }

    public ModulePedagogique intituleModule(String intituleModule) {
        this.intituleModule = intituleModule;
        return this;
    }

    public void setIntituleModule(String intituleModule) {
        this.intituleModule = intituleModule;
    }

    public String getDescriptionModule() {
        return descriptionModule;
    }

    public ModulePedagogique descriptionModule(String descriptionModule) {
        this.descriptionModule = descriptionModule;
        return this;
    }

    public void setDescriptionModule(String descriptionModule) {
        this.descriptionModule = descriptionModule;
    }

    public Set<Parcour> getParcours() {
        return parcours;
    }

    public ModulePedagogique parcours(Set<Parcour> parcours) {
        this.parcours = parcours;
        return this;
    }

    public ModulePedagogique addParcour(Parcour parcour) {
        this.parcours.add(parcour);
        parcour.setModulePedagogique(this);
        return this;
    }

    public ModulePedagogique removeParcour(Parcour parcour) {
        this.parcours.remove(parcour);
        parcour.setModulePedagogique(null);
        return this;
    }

    public void setParcours(Set<Parcour> parcours) {
        this.parcours = parcours;
    }

    public Set<Sujet> getSujets() {
        return sujets;
    }

    public ModulePedagogique sujets(Set<Sujet> sujets) {
        this.sujets = sujets;
        return this;
    }

    public ModulePedagogique addSujet(Sujet sujet) {
        this.sujets.add(sujet);
        sujet.setModulePedagogique(this);
        return this;
    }

    public ModulePedagogique removeSujet(Sujet sujet) {
        this.sujets.remove(sujet);
        sujet.setModulePedagogique(null);
        return this;
    }

    public void setSujets(Set<Sujet> sujets) {
        this.sujets = sujets;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public ModulePedagogique competences(Set<Competence> competences) {
        this.competences = competences;
        return this;
    }

    public ModulePedagogique addCompetence(Competence competence) {
        this.competences.add(competence);
        competence.setModulePedagogique(this);
        return this;
    }

    public ModulePedagogique removeCompetence(Competence competence) {
        this.competences.remove(competence);
        competence.setModulePedagogique(null);
        return this;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    public Set<Devoir> getDevoirs() {
        return devoirs;
    }

    public ModulePedagogique devoirs(Set<Devoir> devoirs) {
        this.devoirs = devoirs;
        return this;
    }

    public ModulePedagogique addDevoir(Devoir devoir) {
        this.devoirs.add(devoir);
        devoir.setModulePedagogique(this);
        return this;
    }

    public ModulePedagogique removeDevoir(Devoir devoir) {
        this.devoirs.remove(devoir);
        devoir.setModulePedagogique(null);
        return this;
    }

    public void setDevoirs(Set<Devoir> devoirs) {
        this.devoirs = devoirs;
    }

    public User getAdmin() {
        return admin;
    }

    public ModulePedagogique admin(User user) {
        this.admin = user;
        return this;
    }

    public void setAdmin(User user) {
        this.admin = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModulePedagogique)) {
            return false;
        }
        return id != null && id.equals(((ModulePedagogique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ModulePedagogique{" +
            "id=" + getId() +
            ", codeModule='" + getCodeModule() + "'" +
            ", intituleModule='" + getIntituleModule() + "'" +
            ", descriptionModule='" + getDescriptionModule() + "'" +
            "}";
    }
}
