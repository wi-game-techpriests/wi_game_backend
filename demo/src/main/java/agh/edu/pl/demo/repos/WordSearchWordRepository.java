package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.WordSearchWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordSearchWordRepository extends JpaRepository<WordSearchWord,Long> {

    @Query(value = "SELECT * FROM word_search_words ORDER BY RANDOM() LIMIT 6", nativeQuery = true)
    List<WordSearchWord> findRandomWords();
}
