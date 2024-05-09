package com.movieflix.movieApi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.dto.MovieRequestDto;
import com.movieflix.movieApi.dto.MovieResponseDto;
import com.movieflix.movieApi.exceptions.EmptyFileException;
import com.movieflix.movieApi.services.FileService;
import com.movieflix.movieApi.services.MovieService;
import com.movieflix.movieApi.utils.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/movie")
@CrossOrigin(origins = "*")
public class MovieController {
    private final MovieService movieService;
    private final FileService fileService;

    public MovieController(MovieService movieService, FileService fileService) {
        this.movieService = movieService;
        this.fileService = fileService;
    }

    @Value("${project.files}")
    private String path;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add-movie")
    public ResponseEntity<MovieResponseDto> addMovieHandler(@RequestPart MultipartFile file, @RequestPart MultipartFile poster, @RequestPart MovieRequestDto movie) throws IOException, EmptyFileException {
        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty! Please send another file");
        }

        if (poster.isEmpty()) {
            throw new EmptyFileException("Poster is empty! Please send another poster");
        }
        //MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(movie, file, poster), HttpStatus.CREATED);
    }

    @PostMapping(path = "add", consumes = "multipart/form-data")
    public ResponseEntity<MovieResponseDto> saveEmployee(@RequestPart MultipartFile file,
                                                         @RequestPart MultipartFile poster,
                                                         @RequestParam String title,
                                                         @RequestParam String director,
                                                         @RequestParam String studio,
                                                         @RequestParam String releaseYear
    ) throws EmptyFileException, IOException {
        System.out.println(title);
        System.out.println(director);
        System.out.println(studio);
        System.out.println(releaseYear);
        System.out.println(poster.getSize());
        if (poster.isEmpty()) {
            throw new EmptyFileException("File is empty! Please send another file");
        }
        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty! Please send another file");
        }
        //MovieDto dto = convertToMovieDto(movieDto);
        MovieRequestDto movie = new MovieRequestDto(title, director, studio, releaseYear);
        return new ResponseEntity<>(movieService.addMovie(movie, file, poster), HttpStatus.CREATED);
    }

//    @GetMapping
//    public String get(HttpServletResponse response) throws IOException {
//        //return new FileUrlResource("https://storage.baza.net/video/");
//        return new FileUrlResource("files/The.Matrix.1999.REMASTERED.BDRip.1080p.mp4").getURI().toString();
//    }

    @GetMapping
    public ResponseEntity<FileSystemResource> serviceFileHandler(HttpServletResponse response) throws IOException {
        //return ;
        FileSystemResource fileSystemResource = new FileSystemResource("files\\The.Matrix.1999.REMASTERED.BDRip.1080p.mp4");
        return ResponseEntity.ok().body(fileSystemResource);
    }

//    @GetMapping
//    public @ResponseBody ResponseEntity<byte[]> getImage() throws IOException {
//        InputStream is = fileService.getResourceFile(path, "The.Matrix.1999.REMASTERED.BDRip.1080p.mp4");
//        assert is != null;
//        byte[] videoResource = IOUtils.toByteArray(is);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType("video/mp4"))
//                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=video_%s.%s", 1, "mp4"))
//                .body(videoResource);
//    }

//    @GetMapping
//    public ResponseEntity<StreamingResponseBody> streamLargeFile() {
//        StreamingResponseBody stream = outputStream -> {
//            Path path = Paths.get("files", URI.create("The.Matrix.1999.REMASTERED.BDRip.1080p.mp4").toString());
//            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path.toFile()))) {
//                byte[] buffer = new byte[1024];
//                int len;
//                while ((len = in.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, len);
//                }
//            }
//        };
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "The.Matrix.1999.REMASTERED.BDRip.1080p.mp4");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(stream);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieHandler(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<MovieResponseDto>> getAllMoviesHandler() {
//        return ResponseEntity.ok(movieService.getAllMovie());
//    }

    @PutMapping("/update")
    public ResponseEntity<MovieResponseDto> updateMovieHandler(@RequestPart MultipartFile file,
                                                               @RequestPart MultipartFile poster,
                                                               @RequestPart String movieDto) throws IOException {
        if (file.isEmpty()) file = null;
        MovieRequestDto newMovieRequestDto = convertToMovieRequestDto(movieDto);
        return ResponseEntity.ok(movieService.updateMovie(newMovieRequestDto, file, poster));
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
    @CrossOrigin(origins = "*")
    @GetMapping("/all")
    public ResponseEntity<MoviePageResponse> getMoviesWithPaginationHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize, sortBy, sortDir));
    }

    private MovieResponseDto convertToMovieResponseDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieResponseDto.class);
    }

    private MovieRequestDto convertToMovieRequestDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieRequestDto.class);
    }

}
