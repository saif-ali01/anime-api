package com.anime.api.service;

import com.anime.api.model.WallpaperModel;
import com.anime.api.model.AnimeModel;
import com.anime.api.repository.WallpaperRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WallpaperService {

    private final WallpaperRepository wallpaperRepository;
    private final AnimeService animeService;

    public WallpaperService(WallpaperRepository wallpaperRepository, AnimeService animeService) {
        this.wallpaperRepository = wallpaperRepository;
        this.animeService = animeService;
    }

    public WallpaperModel addWallpaper(WallpaperModel wallpaper) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(wallpaper.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + wallpaper.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        wallpaper.setAnimeId(animeId);
        return wallpaperRepository.save(wallpaper);
    }

    public List<WallpaperModel> getWallpapersByAnime(String animeId) {
        return wallpaperRepository.findByAnimeId(animeId);
    }

    public Optional<WallpaperModel> getWallpaperById(String id) {
        return wallpaperRepository.findById(id);
    }

    // New search method
    public Page<WallpaperModel> searchWallpapers(String title, String resolution, String animeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wallpaperRepository.searchWallpapers(
            title != null ? title : "",         // Default to empty string if null
            resolution != null ? resolution : "", // Default to empty string if null
            animeId != null ? animeId : "",     // Default to empty string if null
            pageable
        );
    }

    public Optional<WallpaperModel> updateWallpaper(String id, WallpaperModel updatedWallpaper) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(updatedWallpaper.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + updatedWallpaper.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        updatedWallpaper.setAnimeId(animeId);

        return wallpaperRepository.findById(id)
                .map(existingWallpaper -> {
                    existingWallpaper.setAnimeId(updatedWallpaper.getAnimeId());
                    existingWallpaper.setAnimeName(updatedWallpaper.getAnimeName());
                    existingWallpaper.setTitle(updatedWallpaper.getTitle());
                    existingWallpaper.setImageUrl(updatedWallpaper.getImageUrl());
                    existingWallpaper.setResolution(updatedWallpaper.getResolution());
                    existingWallpaper.setDescription(updatedWallpaper.getDescription());
                    return wallpaperRepository.save(existingWallpaper);
                });
    }

    public void deleteWallpaper(String id) {
        wallpaperRepository.deleteById(id);
    }
}