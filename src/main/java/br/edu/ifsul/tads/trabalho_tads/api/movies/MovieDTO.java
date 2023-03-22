package br.edu.ifsul.tads.trabalho_tads.api.movies;

import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class MovieDTO {
    private Long id;
    private String name;
    private String plot;
    private int year;
    private int duration;
    private String image;

    public static MovieDTO create(Movie m) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(m, MovieDTO.class);
    }
}
