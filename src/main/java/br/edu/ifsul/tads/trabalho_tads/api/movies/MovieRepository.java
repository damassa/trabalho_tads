package br.edu.ifsul.tads.trabalho_tads.api.movies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findByName(String name);
}
