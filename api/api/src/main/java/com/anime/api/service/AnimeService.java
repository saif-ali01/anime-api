package com.anime.api.service;

import com.anime.api.model.*;
import com.anime.api.repository.AnimeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final EpisodeService episodeService;
    private final AudioService audioService;
    private final ClipService clipService;       // Required for getClipsForAnime
    private final WallpaperService wallpaperService;

    public AnimeService(AnimeRepository animeRepository, EpisodeService episodeService, 
                        AudioService audioService, ClipService clipService, 
                        WallpaperService wallpaperService) {
        this.animeRepository = animeRepository;
        this.episodeService = episodeService;
        this.audioService = audioService;
        this.clipService = clipService;
        this.wallpaperService = wallpaperService;
    }

    public AnimeModel addAnime(AnimeModel anime) {
        return animeRepository.save(anime);
    }

    public Page<AnimeModel> getAllAnimes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return animeRepository.findAll(pageable);
    }

    public Optional<AnimeModel> getAnimeById(String id) {
        return animeRepository.findById(id);
    }

    public List<AnimeModel> getAnimesByStatus(AnimeModel.AnimeStatus status) {
        return animeRepository.findByStatus(status);
    }

    public List<AnimeModel> getAnimesByGenre(String genre) {
        return animeRepository.findByGenresContaining(genre);
    }

    public List<AnimeModel> getAnimesByTitle(String title) {
        return animeRepository.findByTitleContaining(title);
    }

    public List<AnimeModel> getAnimesByTag(String tag) {
        return animeRepository.findByTagsContaining(tag);
    }

    public Page<AnimeModel> searchAnimes(String genre, AnimeModel.AnimeStatus status, Double minRating, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return animeRepository.findByGenresAndStatusAndRatingGreaterThanEqual(genre, status, minRating, pageable);
    }

    public List<EpisodeModel> getEpisodesForAnime(String animeId) {
        return episodeService.getEpisodesByAnime(animeId);
    }

    public List<AudioModel> getAudioForAnime(String animeId) {
        return audioService.getAudioByAnime(animeId);
    }

    // Added method to fix the error
    public List<ClipModel> getClipsForAnime(String animeId) {
        return clipService.getClipsByAnime(animeId);
    }

    public List<WallpaperModel> getWallpapersForAnime(String animeId) {
        return wallpaperService.getWallpapersByAnime(animeId);
    }

    public Optional<AnimeModel> updateAnime(String id, AnimeModel updatedAnime) {
        return animeRepository.findById(id)
                .map(existingAnime -> {
                    existingAnime.setTitle(updatedAnime.getTitle());
                    existingAnime.setDescription(updatedAnime.getDescription());
                    existingAnime.setGenres(updatedAnime.getGenres());
                    existingAnime.setReleaseYear(updatedAnime.getReleaseYear());
                    existingAnime.setEpisodeCount(updatedAnime.getEpisodeCount());
                    existingAnime.setThumbnailUrl(updatedAnime.getThumbnailUrl());
                    existingAnime.setStatus(updatedAnime.getStatus());
                    existingAnime.setRating(updatedAnime.getRating());
                    existingAnime.setStudio(updatedAnime.getStudio());
                    existingAnime.setTags(updatedAnime.getTags());
                    existingAnime.setSourceMaterial(updatedAnime.getSourceMaterial());
                    existingAnime.setStartDate(updatedAnime.getStartDate());
                    existingAnime.setEndDate(updatedAnime.getEndDate());
                    existingAnime.setLanguageOptions(updatedAnime.getLanguageOptions());
                    return animeRepository.save(existingAnime);
                });
    }

    public void deleteAnime(String id) {
        List<EpisodeModel> episodes = episodeService.getEpisodesByAnime(id);
        episodes.forEach(episode -> episodeService.deleteEpisode(episode.getId()));
        List<AudioModel> audioList = audioService.getAudioByAnime(id);
        audioList.forEach(audio -> audioService.deleteAudio(audio.getId()));
        List<ClipModel> clips = clipService.getClipsByAnime(id);
        clips.forEach(clip -> clipService.deleteClip(clip.getId()));
        List<WallpaperModel> wallpapers = wallpaperService.getWallpapersByAnime(id);
        wallpapers.forEach(wallpaper -> wallpaperService.deleteWallpaper(wallpaper.getId()));
        animeRepository.deleteById(id);
    }
}