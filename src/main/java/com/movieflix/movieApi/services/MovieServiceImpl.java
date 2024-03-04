package com.movieflix.movieApi.services;

import com.movieflix.movieApi.dto.MovieDto;
import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.entries.Movie;
import com.movieflix.movieApi.exceptions.FileExistsException;
import com.movieflix.movieApi.exceptions.MovieNotFoundException;
import com.movieflix.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. upload the file
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists! Please enter another file name!");
        }
        String uploadedFileName = fileService.uploadFile(path, file);
        // 2. set the value of field 'poster' as filename
        movieDto.setPoster(uploadedFileName);
        // 3. map dto to Movie object
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        // 4. save the movie object -< saved Movie object
        Movie savedMovie = movieRepository.save(movie);
        // 5. generate the posterUrl
        String posterUrl = baseUrl + "/file/" + uploadedFileName;
        // 6. map Movie object to DTO object and return it
        return new MovieDto(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
    }

    @Override
    public MovieDto getMovie(Integer id) {
        // 1. check the data id DB and if exists, etch the data if given ID
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + id));
        // 2. generate posterUrl
        String posterUrl = baseUrl + "/file/" + movie.getPoster();
        // 3. map to MovieDto object and return it
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // 1. check if movie object exists with given movieId
        Movie mv = movieRepository.findById(movieDto.getId()).orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + movieDto.getId()));

        String fileName = mv.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }
        movieDto.setPoster(fileName);

        Movie movie = new Movie(
                mv.getId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        movieRepository.save(movie);
        String posterUrl = baseUrl + "/file/" + movie.getPoster();

        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }

    @Override
    public String deleteMovie(Integer id) throws IOException {
        Movie mv = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + id));
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));
        movieRepository.delete(mv);
        return "Movie delete with id = " + id;
    }

    @Override
    public List<MovieDto> getAllMovie() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = new ArrayList<>();
        // 1. fetch all data from DB
        // 2. iterate through the list, generate posterUrl for each movie obj,
        // and map to MovieDto obj
        for (Movie movie: movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();

        List<MovieDto> movieDtos = new ArrayList<>();
        // 1. fetch all data from DB
        // 2. iterate through the list, generate posterUrl for each movie obj,
        // and map to MovieDto obj
        for (Movie movie: movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }
        return new MoviePageResponse(movieDtos, pageNumber, pageSize, moviePage.getTotalElements(), moviePage.getTotalPages(), moviePage.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();

        List<MovieDto> movieDtos = new ArrayList<>();
        // 1. fetch all data from DB
        // 2. iterate through the list, generate posterUrl for each movie obj,
        // and map to MovieDto obj
        for (Movie movie: movies) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieDto movieDto = new MovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            );
            movieDtos.add(movieDto);
        }
        return new MoviePageResponse(movieDtos, pageNumber, pageSize, moviePage.getTotalElements(), moviePage.getTotalPages(), moviePage.isLast());

    }
}
