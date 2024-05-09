package com.movieflix.movieApi.services;

import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.dto.MovieRequestDto;
import com.movieflix.movieApi.dto.MovieResponseDto;
import com.movieflix.movieApi.entries.Movie;
import com.movieflix.movieApi.exceptions.MovieNotFoundException;
import com.movieflix.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;

    @Value("${external.resource.movies}")
    private String moviesPath;

    @Value("${external.resource.posters}")
    private String postersPath;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieResponseDto addMovie(MovieRequestDto movieRequestDto, MultipartFile file, MultipartFile poster) throws IOException {
        // 1. upload the file
        String uploadedFileName;
        if (Files.exists(Paths.get(moviesPath + File.separator + file.getOriginalFilename()))) {
            //throw new FileExistsException("File already exists! Please enter another file name!");
        } else {
            uploadedFileName = fileService.uploadFile(moviesPath, file);
        }

        String uploadedPosterName;
        if (Files.exists(Paths.get(postersPath + File.separator + poster.getOriginalFilename()))) {
            //throw new FileExistsException("Poster already exists! Please enter another poster name!");
        } else {
            uploadedPosterName = fileService.uploadFile(postersPath, poster);
        }


        // 2. set the value of field 'poster' as filename
        //movieDto.setPoster(uploadedFileName);
        // 3. map dto to Movie object


        Movie movie = new Movie(
                null,
                movieRequestDto.getTitle(),
                movieRequestDto.getDirector(),
                movieRequestDto.getStudio(),
                new HashSet<>(),
                Integer.parseInt(movieRequestDto.getReleaseYear()),
                file.getOriginalFilename(),
                poster.getOriginalFilename()
        );
        // 4. save the movie object -< saved Movie object
        Movie savedMovie = movieRepository.save(movie);
        // 5. generate the posterUrl
        // 6. map Movie object to DTO object and return it
        return new MovieResponseDto(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                //savedMovie.getMovieCast(),
                savedMovie.getReleaseYear().toString(),
                savedMovie.getFile(),
                savedMovie.getPoster()
        );
    }

    @Override
    public MovieResponseDto getMovie(Integer id) {
        // 1. check the data id DB and if exists, etch the data if given ID
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + id));
        // 2. generate posterUrl
        // 3. map to MovieDto object and return it
        return new MovieResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                //movie.getMovieCast(),
                movie.getReleaseYear().toString(),
                movie.getFile(),
                movie.getPoster()
        );
    }

    @Override
    public MovieResponseDto updateMovie(MovieRequestDto movieRequestDto, MultipartFile file, MultipartFile poster) throws IOException {
        // 1. check if movie object exists with given movieId
        Movie mv = movieRepository.findById(1).orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + 1));

        String fileName = mv.getFile();
        String posterName = mv.getPoster();

        if (file != null) {
            Files.deleteIfExists(Paths.get(moviesPath + File.separator + fileName));
            fileName = fileService.uploadFile(moviesPath, file);
        }

        if (poster != null) {
            Files.deleteIfExists(Paths.get(postersPath + File.separator + fileName));
            posterName = fileService.uploadFile(postersPath, poster);
        }

        Movie movie = new Movie(
                mv.getId(),
                movieRequestDto.getTitle(),
                movieRequestDto.getDirector(),
                movieRequestDto.getStudio(),
                new HashSet<>(),//movieDto.getMovieCast(),
                Integer.getInteger(movieRequestDto.getReleaseYear()),
                fileName,
                posterName
        );
        movieRepository.save(movie);

        return new MovieResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                //movie.getMovieCast(),
                movie.getReleaseYear().toString(),
                movie.getFile(),
                movie.getPoster()
                //posterUrl
        );
    }

    @Override
    public String deleteMovie(Integer id) throws IOException {
        Movie mv = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found with id = " + id));
        Files.deleteIfExists(Paths.get(moviesPath + File.separator + mv.getFile()));
        Files.deleteIfExists(Paths.get(postersPath + File.separator + mv.getPoster()));
        movieRepository.delete(mv);
        return "Movie delete with id = " + id;
    }

    @Override
    public List<MovieResponseDto> getAllMovie() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieResponseDto> movieResponseDtos = new ArrayList<>();
        // 1. fetch all data from DB
        // 2. iterate through the list, generate posterUrl for each movie obj,
        // and map to MovieDto obj
        for (Movie movie : movies) {
            MovieResponseDto movieResponseDto = new MovieResponseDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
//                    movie.getMovieCast(),
                    movie.getReleaseYear().toString(),
                    movie.getFile(),
                    movie.getPoster()
            );
            movieResponseDtos.add(movieResponseDto);
        }
        return movieResponseDtos;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();

        List<MovieResponseDto> movieResponseDtos = new ArrayList<>();
        // 1. fetch all data from DB
        // 2. iterate through the list, generate posterUrl for each movie obj,
        // and map to MovieDto obj
        for (Movie movie : movies) {
            MovieResponseDto movieResponseDto = new MovieResponseDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
//                    movie.getMovieCast(),
                    movie.getReleaseYear().toString(),
                    movie.getFile(),
                    movie.getPoster()
            );
            movieResponseDtos.add(movieResponseDto);
        }
        return new MoviePageResponse(movieResponseDtos, pageNumber, pageSize, moviePage.getTotalElements(), moviePage.getTotalPages(), moviePage.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();

        List<MovieResponseDto> movieResponseDtos = new ArrayList<>();
        // 1. fetch all data from DB
        // 2. iterate through the list, generate posterUrl for each movie obj,
        // and map to MovieDto obj
        for (Movie movie : movies) {
            MovieResponseDto movieResponseDto = new MovieResponseDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
//                    movie.getMovieCast(),
                    movie.getReleaseYear().toString(),
                    movie.getFile(),
                    movie.getPoster()
            );
            movieResponseDtos.add(movieResponseDto);
        }
        return new MoviePageResponse(movieResponseDtos, pageNumber, pageSize, moviePage.getTotalElements(), moviePage.getTotalPages(), moviePage.isLast());

    }
}
