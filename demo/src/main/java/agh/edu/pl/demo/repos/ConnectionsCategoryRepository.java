package agh.edu.pl.demo.repos;

import agh.edu.pl.demo.model.ConnectionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionsCategoryRepository extends JpaRepository<ConnectionsCategory, Long> {
}
