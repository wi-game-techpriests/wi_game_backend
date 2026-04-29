package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.WordSearchWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordSearchWordRepository extends JpaRepository<WordSearchWord,Long> {
}
