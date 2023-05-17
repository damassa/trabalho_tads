package br.edu.ifsul.tads.trabalho_tads;

import br.edu.ifsul.tads.trabalho_tads.api.movies.Movie;
import br.edu.ifsul.tads.trabalho_tads.api.movies.MovieDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoTadsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest extends BaseAPITest {
    private ResponseEntity<MovieDTO> getMovie(String url) {
        return get(url, MovieDTO.class);
    }

    private ResponseEntity<List<MovieDTO>> getMovies(String url) {
        HttpHeaders headers = getHeaders();

        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<MovieDTO>>() {
        });
    }

    @Test
    public void selectAll() {
        List<MovieDTO> movies = getMovies("/api/v1/movies").getBody();
        assertNotNull(movies);
        assertEquals(3, movies.size());

        movies = getMovies("/api/v1/movies?page=0&size=3").getBody();
        assertNotNull(movies);
        assertEquals(3, movies.size());
    }

    @Test
    public void selectByName() {
        assertEquals(1, getMovies("/api/v1/movies/name/Power").getBody().size());
        assertEquals(1, getMovies("/api/v1/movies/name/The").getBody().size());
        assertEquals(1, getMovies("/api/movies/name/O").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getMovies("/api/v1/movies/name/xxx").getStatusCode());
    }

    @Test
    public void selectById() {
        assertNotNull(getMovie("/api/v1/movies/1"));
        assertNotNull(getMovie("/api/v1/movies/2"));
        assertNotNull(getMovie("/api/v1/movies/3"));

        assertEquals(HttpStatus.NOT_FOUND, getMovie("/api/v1/movies/999").getStatusCode());
    }

    @Test
    public void testInsert() {
        Movie movie = new Movie();
        movie.setName("Godzilla");
        movie.setDuration(65);
        movie.setYear(1998);
        movie.setPlot("Giant ass lizard attacking a city.");
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/2/2e/Godzilla_%281998_Movie_Poster%29.jpg");

        ResponseEntity response = post("/api/v1/movies", movie, null);
        System.out.println(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String location = response.getHeaders().get("location").get(0);
        MovieDTO m = getMovie(location).getBody();

        assertNotNull(m);
        assertEquals("Godzilla", m.getName());
        assertEquals(1998, m.getYear());

        delete(location, null);

        assertEquals(HttpStatus.NOT_FOUND, getMovie(location).getStatusCode());
    }

    @Test
    public void testUpdate() {
        Movie movie = new Movie();
        movie.setName("Godzilla");
        movie.setDuration(65);
        movie.setYear(1998);
        movie.setPlot("Giant ass lizard attacking a city.");
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/2/2e/Godzilla_%281998_Movie_Poster%29.jpg");

        ResponseEntity response = post("/api/v1/movies", movie, null);
        System.out.println(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String location = response.getHeaders().get("location").get(0);
        MovieDTO m = getMovie(location).getBody();

        assertNotNull(m);
        assertEquals("Godzilla", m.getName());
        assertEquals(1998, m.getYear());

        Movie alteredMovie = Movie.create(m);
        alteredMovie.setName("Godzilla 2");

        response = put("/api/v1/movies" + m.getId(), alteredMovie, null);
        System.out.println(response);
        assertEquals("Godzilla 2", alteredMovie.getName());

        delete(location, null);

        assertEquals(HttpStatus.NOT_FOUND, getMovie(location).getStatusCode());
    }

    @Test
    public void testDelete() {
        this.testInsert();
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getMovie("/api/v1/movies/1200");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
