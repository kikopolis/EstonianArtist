package com.kikopolis.repository;

import com.kikopolis.entity.Artist;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    default String convertToJson(final Artist artist) {
        return "{\"id\": " + artist.getId() + ", \"name\": \"" + artist.getName() + "\"}";
    }
    default Iterable<Artist> featuredArtistsListForHome() {
        return findAll();
    }
}
