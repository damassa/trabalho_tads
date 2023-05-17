package br.edu.ifsul.tads.trabalho_tads.api.movies;

import br.edu.ifsul.tads.trabalho_tads.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MovieService {

    @Autowired
    private MovieRepository rep;

    public List<MovieDTO> getMovies() {
        return rep.findAll()
            .stream().map(MovieDTO::create)
            .collect(Collectors.toList());
    }
    public MovieDTO getMovieById(Long id) {
        Optional<Movie> movie = rep.findById(id);
        return movie.map(MovieDTO::create).orElseThrow(() -> new ObjectNotFoundException("Movie not found."));
    }
    public List<MovieDTO> getMovieByName(String name) {
        return rep.findByName(name+"%").stream().map(MovieDTO::create).collect(Collectors.toList());
    }
    public MovieDTO insert(Movie movie) {
        Assert.isNull(movie.getId(), "Couldn't insert!");
        return MovieDTO.create(rep.save(movie));
    }
    public MovieDTO update(Movie movie, Long id) {
        Assert.notNull(id, "Couldn't update!");
        Optional<Movie> optional = rep.findById(id);
        if(optional.isPresent()) {
            Movie db = optional.get();
            db.setName(movie.getName());
            db.setPlot(movie.getPlot());
            db.setYear(movie.getYear());
            db.setDuration(movie.getDuration());
            db.setImage(movie.getImage());
            System.out.println("Movie id " + db.getId());

            rep.save(db);
            return MovieDTO.create(db);
        } else {
            return null;
//            throw new RuntimeException("Couldn't update!");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
