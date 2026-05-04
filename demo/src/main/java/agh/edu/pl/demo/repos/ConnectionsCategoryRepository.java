package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.ConnectionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionsCategoryRepository extends JpaRepository<ConnectionsCategory, Long> {
    @Query(value = "SELECT * FROM connections_categories ORDER BY RANDOM() LIMIT 4", nativeQuery = true)
    List<ConnectionsCategory> findRandomCategories();
}
