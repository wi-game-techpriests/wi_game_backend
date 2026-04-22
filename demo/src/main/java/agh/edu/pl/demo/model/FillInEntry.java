package agh.edu.pl.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "fill_in_entries")
public class FillInEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> fragments;
    private List<String> possibleAnswers;
    private List<String> answers;

    public Long getId() {
        return id;
    }

    public List<String> getFragments() {
        return fragments;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
