package com.anime.api.repository;

import com.anime.api.model.AnimeModel;
import com.anime.api.model.AnimeModel.AnimeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface AnimeRepository extends MongoRepository<AnimeModel, String> {
    @SuppressWarnings("null")
    Page<AnimeModel> findAll( Pageable pageable);

    List<AnimeModel> findByStatus(AnimeStatus status);

    @Query("{ 'genres': { $regex: ?0, $options: 'i' } }")
    List<AnimeModel> findByGenresContaining(String genre);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<AnimeModel> findByTitleContaining(String title);

    @Query("{ 'tags': { $regex: ?0, $options: 'i' } }")
    List<AnimeModel> findByTagsContaining(String tag);

    // Efficient multi-criteria search
    @Query("{ $and: [ " +
            "{ 'genres': { $regex: ?0, $options: 'i' } }, " +
            "{ 'status': ?1 }, " +
            "{ 'rating': { $gte: ?2 } } " +
            "] }")
    Page<AnimeModel> findByGenresAndStatusAndRatingGreaterThanEqual(String genre, AnimeStatus status, Double minRating, Pageable pageable);

    // Sort by rating
    List<AnimeModel> findByGenresContainingOrderByRatingDesc(String genre);
}