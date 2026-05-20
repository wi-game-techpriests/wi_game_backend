package agh.edu.pl.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "connections_categories")
public class ConnectionsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @ElementCollection
    private List<String> words;

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getWords() {
        return words;
    }
}
