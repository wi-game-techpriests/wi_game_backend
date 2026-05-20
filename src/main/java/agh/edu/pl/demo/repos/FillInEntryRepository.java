package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.FillInEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FillInEntryRepository extends JpaRepository<FillInEntry, Long> {
    @Query(value = "SELECT * FROM fill_in_entries ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    FillInEntry findRandomFillInEntry();
}
