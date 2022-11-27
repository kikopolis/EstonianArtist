package com.kikopolis.controller.home;

import com.google.gson.JsonParser;
import com.kikopolis.entity.Artist;
import com.kikopolis.repository.ArtistRepository;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class HomeControllerTest {
    @Inject
    @Client("/")
    private HttpClient client;
    
    @Test
    void testIndex() {
        var response = client.toBlocking().retrieve("/");
        assertThat("response is not empty", !response.isEmpty());
        assertThat("response contains 'Hello World'", response.contains("Hello World"));
    }
    
    @Test
    void testArtistsForHome() {
        var response = client.toBlocking().retrieve("/artists-for-home");
        assertThat("response is not empty", !response.isEmpty());
        assertThat("response contains the list of featured artists", response.contains("featuredArtists"));
        var parsed = JsonParser.parseString(response);
        var featuredArtists = parsed.getAsJsonObject().get("featuredArtists");
        assertThat("featured artists count is not 0", featuredArtists.getAsJsonArray().size() > 0);
        assertThat("featured artists contains artist with name 'Kiko'", featuredArtists.getAsJsonArray().toString().contains("Kiko"));
    }
    
    @MockBean(ArtistRepository.class)
    ArtistRepository artistRepository() {
        var artistRepository = mock(ArtistRepository.class);
        when(artistRepository.findAll()).then(invocation -> {
            var artist1 = new Artist();
            artist1.setName("Kiko");
            var artist2 = new Artist();
            artist2.setName("Muff");
            return List.of(artist1, artist2);
        });
        return artistRepository;
    }
}
