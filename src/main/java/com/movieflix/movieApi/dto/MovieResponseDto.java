package com.movieflix.movieApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDto {
    @NotBlank(message = "Please provide movie`s id")
    private Integer Id;
    @NotBlank(message = "Please provide movie`s title")
    private String title;
    @NotBlank(message = "Please provide movie`s director")
    private String director;
    @NotBlank(message = "Please provide movie`s studio")
    private String studio;
//    private Set<String> movieCast;
    @NotBlank(message = "Please provide movie`s release year")
    private String releaseYear;
    @NotBlank(message = "Please provide movie`s url")
    private String movieUrl;
    @NotBlank(message = "Please provide poster`s url")
    private String posterUrl;
}
