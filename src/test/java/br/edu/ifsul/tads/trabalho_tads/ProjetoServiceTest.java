package br.edu.ifsul.tads.trabalho_tads;
import br.edu.ifsul.tads.trabalho_tads.api.infra.exception.ObjectNotFoundException;
import br.edu.ifsul.tads.trabalho_tads.api.movies.Movie;
import br.edu.ifsul.tads.trabalho_tads.api.movies.MovieDTO;
import br.edu.ifsul.tads.trabalho_tads.api.movies.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProjetoServiceTest {

    @Autowired
    private MovieService service;

    @Test
    public void testGetMovies() {
        List<MovieDTO> movies = service.getMovies();
        assertEquals(2, movies.size());
    }

    @Test
    public void testGetMovieById() {
        MovieDTO m = service.getMovieById(1L);
        assertNotNull("The Exorcist", m.getName());
    }

    @Test
    public void testGetMovieByNome() {
        assertEquals(1, service.getMovieByName("The Exorcist").size());
        assertEquals(1, service.getMovieByName("Power").size());
        assertEquals(1, service.getMovieByName("O Auto  da Compadecida").size());
    }

    @Test
    public void testInsert() {
        Movie movie = new Movie();
        movie.setName("Godzilla");
        movie.setDuration(65);
        movie.setYear(1998);
        movie.setPlot("Giant ass dinosaur attacking a city.");
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/2/2e/Godzilla_%281998_Movie_Poster%29.jpg");

        MovieDTO m = service.insert(movie);

        assertNotNull(m);

        Long id = m.getId();
        assertNotNull(id);
        m = service.getMovieById(id);
        assertNotNull(m);

        assertEquals("Godzilla", m.getName());
        assertEquals(65, m.getDuration());
        assertEquals(1998, m.getYear());
        assertEquals("Giant ass dinosaur attacking a city.", m.getPlot());
        assertEquals("https://upload.wikimedia.org/wikipedia/en/2/2e/Godzilla_%281998_Movie_Poster%29.jpg", m.getImage());

        service.delete(id);
        try {
            service.getMovieById(id);
            fail("Movie didn't get deleted.");
        } catch (ObjectNotFoundException e) {

        }
    }

    @Test
    public void testUpdate() {
        MovieDTO mDTO = service.getMovieById(1L);
        String name = mDTO.getName();
        int duration = mDTO.getDuration();
        int year = mDTO.getYear();
        String plot = mDTO.getPlot();
        String image = mDTO.getImage();
        mDTO.setName("Godzilla 2000");
        mDTO.setDuration(90);
        mDTO.setYear(2000);
        mDTO.setPlot("Giant ass dinosaur attacking a city in Japan.");
        mDTO.setImage("https://upload.wikimedia.org/wikipedia/en/f/f0/Godzilla2000jap.jpg?20171007235516");
        Movie m = Movie.create(mDTO);

        mDTO = service.update(m, m.getId());
        assertNotNull(mDTO);
        assertEquals("Godzilla 2000", mDTO.getName());
        assertEquals(90, mDTO.getDuration());
        assertEquals(2000, mDTO.getYear());
        assertEquals("Giant ass dinosaur attacking a city in Japan.", mDTO.getPlot());
        assertEquals("https://upload.wikimedia.org/wikipedia/en/f/f0/Godzilla2000jap.jpg?20171007235516", mDTO.getImage());

        m.setName(name);
        m.setDuration(duration);
        m.setYear(year);
        m.setPlot(plot);
        m.setImage(image);
        mDTO = service.update(m, m.getId());
        assertNotNull(mDTO);
    }

    @Test
    public void testDelete() {
        Movie movie = new Movie();
        movie.setName("Dracula 3000");
        movie.setDuration(86);
        movie.setYear(2004);
        movie.setPlot("The worst movie in the history of cinema.");
        movie.setImage("https://upload.wikimedia.org/wikipedia/en/thumb/3/33/Dracula_3000_movie_poster.jpg/250px-Dracula_3000_movie_poster.jpg");

        MovieDTO m = service.insert(movie);
        assertNotNull(m);

        Long id = m.getId();
        assertNotNull(id);
        m = service.getMovieById(id);
        assertNotNull(m);

        service.delete(id);
        try {
            service.getMovieById(id);
            fail("Movie didn't get deleted.");
        } catch (ObjectNotFoundException e) {

        }
    }
}
