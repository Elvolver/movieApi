package com.movieflix.movieApi.services;

import com.movieflix.movieApi.dto.MovieDto;
import com.movieflix.movieApi.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer movieId);

    MovieDto updateMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    String deleteMovie(Integer id) throws IOException;
    List<MovieDto> getAllMovie();

    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);
    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize, String sortBy, String dir);
}
