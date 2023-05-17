package br.edu.ifsul.tads.trabalho_tads.api.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value = "SELECT m from Movie m where m.name like ?1")
    List<Movie> findByName(String name);
}
