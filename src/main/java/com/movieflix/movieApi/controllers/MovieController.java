package com.movieflix.movieApi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.movieApi.dto.MovieDto;
import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.exceptions.EmptyFileException;
import com.movieflix.movieApi.services.MovieService;
import com.movieflix.movieApi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException, EmptyFileException {
        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty! Please send another file");
        }
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler() {
        return ResponseEntity.ok(movieService.getAllMovie());
    }

    @PutMapping("/update")
    public ResponseEntity<MovieDto> updateMovieHandler(@RequestPart MultipartFile file,
                                                       @RequestPart String movieDto) throws IOException {
        if (file.isEmpty()) file = null;
        MovieDto newMovieDto = convertToMovieDto(movieDto);
        return ResponseEntity.ok(movieService.updateMovie(newMovieDto, file));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(id));
    }

    @GetMapping("/allMoviesPage")
    public ResponseEntity<MoviePageResponse> getMoviesWithPaginationHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/allMoviesPageSort")
    public ResponseEntity<MoviePageResponse> getMoviesWithPaginationHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
            ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize, sortBy, sortDir));
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }

}
