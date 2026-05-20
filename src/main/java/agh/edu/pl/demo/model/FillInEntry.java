package agh.edu.pl.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fill_in_entries")
public class FillInEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> fragments;

    @OneToMany(mappedBy = "parentEntry", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FillInAnswer> fragmentEntries;



    public FillInEntry() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFragments(List<String> fragments) {
        this.fragments = fragments;
    }

    public void setFragmentEntries(List<FillInAnswer> fragmentEntries) {
        this.fragmentEntries = fragmentEntries;
    }

    public Long getId() {
        return id;
    }

    public List<String> getFragments() {
        return fragments;
    }

    public List<FillInAnswer> getFragmentEntries() {
        return fragmentEntries;
    }
}
