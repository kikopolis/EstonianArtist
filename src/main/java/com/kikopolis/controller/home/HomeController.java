package com.kikopolis.controller.home;

import com.kikopolis.repository.ArtistRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HomeController {
    private final ArtistRepository artistRepository;
    
    public HomeController(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }
    
    @Get("/")
    public HttpResponse<?> index() {
        return HttpResponse.ok("Hello World").contentType(MediaType.APPLICATION_JSON_TYPE);
    }
    
    @Get("/artists-for-home")
    public HttpResponse<?> artistsForHome() {
        var featuredArtists = artistRepository.featuredArtistsListForHome();
        StringBuilder json = new StringBuilder("{\"featuredArtists\": [");
        for (var artist : featuredArtists) {
            json.append(artistRepository.convertToJson(artist)).append(", ");
        }
        json.delete(json.length() - 2, json.length());
        json.append("]}");
        return HttpResponse.ok(json.toString()).contentType(MediaType.APPLICATION_JSON_TYPE);
    }
}
