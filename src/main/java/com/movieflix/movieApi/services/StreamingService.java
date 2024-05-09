package com.movieflix.movieApi.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StreamingService {
    private final ResourceLoader resourceLoader;

    public StreamingService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Mono<Resource> getVideo(String title) {
        Mono<Resource> resource = Mono.fromSupplier(() -> resourceLoader.getResource("file:"+title));
        System.out.println(title);
        return resource;
    }


}
