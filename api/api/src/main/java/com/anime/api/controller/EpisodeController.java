package com.anime.api.controller;

import com.anime.api.model.EpisodeModel;
import com.anime.api.service.EpisodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping
    public ResponseEntity<EpisodeModel> addEpisode(@Valid @RequestBody EpisodeModel episode) {
        EpisodeModel savedEpisode = episodeService.addEpisode(episode);
        return ResponseEntity.ok(savedEpisode);
    }

    @GetMapping("/anime/{animeId}")
    public ResponseEntity<List<EpisodeModel>> getEpisodesByAnime(@PathVariable String animeId) {
        List<EpisodeModel> episodes = episodeService.getEpisodesByAnime(animeId);
        return ResponseEntity.ok(episodes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeModel> getEpisodeById(@PathVariable String id) {
        Optional<EpisodeModel> episode = episodeService.getEpisodeById(id);
        return episode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpisodeModel> updateEpisode(@PathVariable String id, @Valid @RequestBody EpisodeModel updatedEpisode) {
        Optional<EpisodeModel> updated = episodeService.updateEpisode(id, updatedEpisode);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable String id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.noContent().build();
    }
}