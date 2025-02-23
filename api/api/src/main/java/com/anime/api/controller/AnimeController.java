package com.anime.api.controller;

import com.anime.api.model.*;
import com.anime.api.service.AnimeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animes")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @PostMapping
    public ResponseEntity<AnimeModel> addAnime(@Valid @RequestBody AnimeModel anime) {
        AnimeModel savedAnime = animeService.addAnime(anime);
        return ResponseEntity.ok(savedAnime);
    }

    @GetMapping
    public ResponseEntity<Page<AnimeModel>> getAllAnimes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AnimeModel> animes = animeService.getAllAnimes(page, size);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeModel> getAnimeById(@PathVariable String id) {
        Optional<AnimeModel> anime = animeService.getAnimeById(id);
        return anime.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/episodes")
    public ResponseEntity<List<EpisodeModel>> getEpisodesForAnime(@PathVariable String id) {
        List<EpisodeModel> episodes = animeService.getEpisodesForAnime(id);
        return ResponseEntity.ok(episodes);
    }

    @GetMapping("/{id}/audio")
    public ResponseEntity<List<AudioModel>> getAudioForAnime(@PathVariable String id) {
        List<AudioModel> audioList = animeService.getAudioForAnime(id);
        return ResponseEntity.ok(audioList);
    }

    @GetMapping("/{id}/clips")
    public ResponseEntity<List<ClipModel>> getClipsForAnime(@PathVariable String id) {
        List<ClipModel> clips = animeService.getClipsForAnime(id);
        return ResponseEntity.ok(clips);
    }

    @GetMapping("/{id}/wallpapers")
    public ResponseEntity<List<WallpaperModel>> getWallpapersForAnime(@PathVariable String id) {
        List<WallpaperModel> wallpapers = animeService.getWallpapersForAnime(id);
        return ResponseEntity.ok(wallpapers);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AnimeModel>> getAnimesByStatus(@PathVariable AnimeModel.AnimeStatus status) {
        List<AnimeModel> animes = animeService.getAnimesByStatus(status);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<AnimeModel>> getAnimesByGenre(@PathVariable String genre) {
        List<AnimeModel> animes = animeService.getAnimesByGenre(genre);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<AnimeModel>> getAnimesByTitle(@PathVariable String title) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(title);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<AnimeModel>> getAnimesByTag(@PathVariable String tag) {
        List<AnimeModel> animes = animeService.getAnimesByTag(tag);
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AnimeModel>> searchAnimes(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) AnimeModel.AnimeStatus status,
            @RequestParam(required = false) Double minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AnimeModel> animes = animeService.searchAnimes(genre, status, minRating, page, size);
        return ResponseEntity.ok(animes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimeModel> updateAnime(@PathVariable String id, @Valid @RequestBody AnimeModel updatedAnime) {
        Optional<AnimeModel> updated = animeService.updateAnime(id, updatedAnime);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable String id) {
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }
}