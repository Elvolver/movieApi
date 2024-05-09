package com.movieflix.movieApi.controllers;

import com.movieflix.movieApi.dto.MovieResponseDto;
import com.movieflix.movieApi.services.MovieService;
import com.movieflix.movieApi.services.StreamingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;

@RestController
@RequestMapping("/video")

public class StreamingController {

    private final StreamingService streamingService;
    private final MovieService movieService;
    public static final int BYTE_RANGE = 128;

    public StreamingController(StreamingService streamingService, MovieService movieService) {
        this.streamingService = streamingService;
        this.movieService = movieService;
    }

    @Value("${external.resource.movies}")
    private String movies;

    @Value("${external.resource.posters}")
    private String posters;

//    @GetMapping(value = "video/{title}", produces = "video/mp4")
    @GetMapping(produces = "video/mp4", value = "/{id}")
    public Mono<Resource> getVideos(@RequestHeader("Range") String range, @PathVariable Integer id){
        MovieResponseDto movie = movieService.getMovie(id);
        return streamingService.getVideo(movies + File.separator + movie.getMovieUrl());
    }


    @GetMapping(value = "/poster/{id}")
    public Mono<Resource> getPoster(@PathVariable Integer id){
        MovieResponseDto movie = movieService.getMovie(id);
        return streamingService.getVideo(posters + File.separator + movie.getPosterUrl());
    }
}
