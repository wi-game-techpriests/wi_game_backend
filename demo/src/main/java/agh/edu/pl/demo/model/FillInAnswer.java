package agh.edu.pl.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fill_in_answers")
public class FillInAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore

    private Long id;

    private int answerNumber;
    private String answer;
    //do different than true?
    @ElementCollection
    private List<String> possibleAnswers;

    @ManyToOne
    @JoinColumn(name = "parent_entry_id")
    @JsonBackReference
    private FillInEntry parentEntry;

    public FillInAnswer() {
    }

    public FillInEntry getParentEntry() {
        return parentEntry;
    }

    public void setParentEntry(FillInEntry parentEntry) {
        this.parentEntry = parentEntry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
