package br.edu.ifsul.tads.trabalho_tads.api.movies;

import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name="Movie.findByName", query="select m from Movie m where m.name like :name"),
})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String plot;
    private int year;
    private int duration;
    private String image;

    public static Movie create(MovieDTO m) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(m, Movie.class);
    }
}
