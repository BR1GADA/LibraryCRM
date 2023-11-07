package org.peachSpring.app.services;

import org.peachSpring.app.models.Genre;
import org.peachSpring.app.repositories.GenresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresService {
    private final GenresRepository genresRepository;

    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }
    public List<Genre> findAll(){
        return genresRepository.findAll();
    }
}
