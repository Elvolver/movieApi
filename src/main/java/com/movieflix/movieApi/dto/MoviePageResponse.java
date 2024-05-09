package com.movieflix.movieApi.dto;

import java.util.List;

public record MoviePageResponse(List<MovieResponseDto> movies,
                                Integer pageNumber,
                                Integer pageSize,
                                long totalElements,
                                long totalPages,
                                boolean isLast) {
}
