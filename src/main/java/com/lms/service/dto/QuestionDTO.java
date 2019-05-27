package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String numQuestion;

    @NotNull
    private String contenuQuestion;


    private Long situationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(String numQuestion) {
        this.numQuestion = numQuestion;
    }

    public String getContenuQuestion() {
        return contenuQuestion;
    }

    public void setContenuQuestion(String contenuQuestion) {
        this.contenuQuestion = contenuQuestion;
    }

    public Long getSituationId() {
        return situationId;
    }

    public void setSituationId(Long situationId) {
        this.situationId = situationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (questionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id=" + getId() +
            ", numQuestion='" + getNumQuestion() + "'" +
            ", contenuQuestion='" + getContenuQuestion() + "'" +
            ", situation=" + getSituationId() +
            "}";
    }
}
