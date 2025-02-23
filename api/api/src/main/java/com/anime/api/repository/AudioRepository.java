package com.anime.api.repository;

import com.anime.api.model.AudioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface AudioRepository extends MongoRepository<AudioModel, String> {
    List<AudioModel> findByAnimeId(String animeId); // Get all by anime ID

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<AudioModel> findByTitleContaining(String title); // Get by name (title)

    // Search with multiple criteria and pagination
    @Query("{ $and: [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +
            "{ 'type': ?1 }, " +
            "{ 'animeId': ?2 } " +
            "] }")
    Page<AudioModel> searchAudio(String title, String type, String animeId, Pageable pageable);
}