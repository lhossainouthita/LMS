package com.lms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lms.domain.Reponse} entity.
 */
public class ReponseDTO implements Serializable {

    private Long id;

    @NotNull
    private String contenuReponse;


    private Long questionId;

    private String questionContenuQuestion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenuReponse() {
        return contenuReponse;
    }

    public void setContenuReponse(String contenuReponse) {
        this.contenuReponse = contenuReponse;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContenuQuestion() {
        return questionContenuQuestion;
    }

    public void setQuestionContenuQuestion(String questionContenuQuestion) {
        this.questionContenuQuestion = questionContenuQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReponseDTO reponseDTO = (ReponseDTO) o;
        if (reponseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reponseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReponseDTO{" +
            "id=" + getId() +
            ", contenuReponse='" + getContenuReponse() + "'" +
            ", question=" + getQuestionId() +
            ", question='" + getQuestionContenuQuestion() + "'" +
            "}";
    }
}
