package com.anime.api.repository;

import com.anime.api.model.EpisodeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EpisodeRepository extends MongoRepository<EpisodeModel, String> {
    List<EpisodeModel> findByAnimeId(String animeId); // Fetch episodes by Anime ID
}
