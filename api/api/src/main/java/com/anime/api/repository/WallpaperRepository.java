package com.anime.api.repository;

import com.anime.api.model.WallpaperModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface WallpaperRepository extends MongoRepository<WallpaperModel, String> {
    List<WallpaperModel> findByAnimeId(String animeId); // Fetch all wallpapers by anime ID

    // Search with multiple criteria and pagination
    @Query("{ $and: [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +
            "{ 'resolution': { $regex: ?1, $options: 'i' } }, " +
            "{ 'animeId': ?2 } " +
            "] }")
    Page<WallpaperModel> searchWallpapers(String title, String resolution, String animeId, Pageable pageable);
}