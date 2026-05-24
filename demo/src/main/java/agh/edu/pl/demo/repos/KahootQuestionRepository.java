package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.KahootQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KahootQuestionRepository extends JpaRepository<KahootQuestion,Long> {


    @Query(value = "SELECT * FROM kahoot_question ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
    List<KahootQuestion> randomKahootQuestion();
}
