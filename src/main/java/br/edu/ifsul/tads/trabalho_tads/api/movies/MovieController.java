package br.edu.ifsul.tads.trabalho_tads.api.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> selectAll() {
        List<MovieDTO> movies = service.getMovies();
        return ResponseEntity.ok(movies);
    }
    @GetMapping("{id}")
    public ResponseEntity<MovieDTO> selectById(@PathVariable("id") Long id) {
        MovieDTO movie = service.getMovieById(id);
        return ResponseEntity.ok(movie);
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<List<MovieDTO>> selectByName(@PathVariable("name") String name) {
        List<MovieDTO> movies = service.getMovieByName(name);
        return movies.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(movies);
    }
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<String> insert(@RequestBody Movie movie) {
        MovieDTO m = service.insert(movie);
        URI location = getUri(m.getId());
        return ResponseEntity.created(location).build();
    }
    @PutMapping("{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable("id") Long id, @RequestBody Movie movie) {
        movie.setId(id);
        MovieDTO m = service.update(movie, id);
        return m !=null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
