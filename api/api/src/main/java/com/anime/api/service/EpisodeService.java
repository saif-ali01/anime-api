package com.anime.api.service;

import com.anime.api.model.AnimeModel;
import com.anime.api.model.EpisodeModel;
import com.anime.api.repository.EpisodeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final AnimeService animeService; // Inject AnimeService

    public EpisodeService(EpisodeRepository episodeRepository, AnimeService animeService) {
        this.episodeRepository = episodeRepository;
        this.animeService = animeService;
    }

    public EpisodeModel addEpisode(EpisodeModel episode) {
        // Fetch anime by name and set animeId
        List<AnimeModel> animes = animeService.getAnimesByTitle(episode.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + episode.getAnimeName());
        }
        // Assuming the first match is the intended anime (could refine with exact match logic)
        String animeId = animes.get(0).getId();
        episode.setAnimeId(animeId);

        return episodeRepository.save(episode);
    }

    public List<EpisodeModel> getEpisodesByAnime(String animeId) {
        return episodeRepository.findByAnimeId(animeId);
    }

    public Optional<EpisodeModel> getEpisodeById(String id) {
        return episodeRepository.findById(id);
    }

    public Optional<EpisodeModel> updateEpisode(String id, EpisodeModel updatedEpisode) {
        // Fetch anime by name and update animeId during update as well
        List<AnimeModel> animes = animeService.getAnimesByTitle(updatedEpisode.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + updatedEpisode.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        updatedEpisode.setAnimeId(animeId);

        return episodeRepository.findById(id)
                .map(existingEpisode -> {
                    existingEpisode.setAnimeId(updatedEpisode.getAnimeId());
                    existingEpisode.setAnimeName(updatedEpisode.getAnimeName());
                    existingEpisode.setEpisodeNumber(updatedEpisode.getEpisodeNumber());
                    existingEpisode.setTitle(updatedEpisode.getTitle());
                    existingEpisode.setDescription(updatedEpisode.getDescription());
                    existingEpisode.setVideoUrl(updatedEpisode.getVideoUrl());
                    existingEpisode.setType(updatedEpisode.getType());
                    existingEpisode.setAirDate(updatedEpisode.getAirDate());
                    return episodeRepository.save(existingEpisode);
                });
    }

    public void deleteEpisode(String id) {
        episodeRepository.deleteById(id);
    }
}