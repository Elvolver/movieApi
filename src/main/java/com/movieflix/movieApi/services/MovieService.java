package com.movieflix.movieApi.services;

import com.movieflix.movieApi.dto.MovieRequestDto;
import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.dto.MovieResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieResponseDto addMovie(MovieRequestDto movieRequestDto, MultipartFile file, MultipartFile poster) throws IOException;
    MovieResponseDto getMovie(Integer movieId);

    MovieResponseDto updateMovie(MovieRequestDto movieRequestDto, MultipartFile file, MultipartFile poster) throws IOException;
    String deleteMovie(Integer id) throws IOException;
    List<MovieResponseDto> getAllMovie();

    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);
    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize, String sortBy, String dir);
}
