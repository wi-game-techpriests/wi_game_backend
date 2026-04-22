package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.FillInEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FillInEntryRepository extends JpaRepository<FillInEntry, Long> {
}
