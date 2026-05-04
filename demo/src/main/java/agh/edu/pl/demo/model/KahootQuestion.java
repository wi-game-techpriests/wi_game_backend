package agh.edu.pl.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class KahootQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private String answer;
    @ElementCollection
    private List<String> otherChoices;

    public KahootQuestion() {
    }

    public List<String> getOtherChoices() {
        return otherChoices;
    }

    public void setOtherChoices(List<String> otherChoices) {
        this.otherChoices = otherChoices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }
}
