package com.anime.api.controller;

import com.anime.api.model.AudioModel;
import com.anime.api.service.AudioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final AudioService audioService;

    public AudioController(AudioService audioService) {
        this.audioService = audioService;
    }

    // Create
    @PostMapping
    public ResponseEntity<AudioModel> addAudio(@Valid @RequestBody AudioModel audio) {
        AudioModel savedAudio = audioService.addAudio(audio);
        return ResponseEntity.ok(savedAudio);
    }

    // Get all audio (paginated)
    @GetMapping
    public ResponseEntity<Page<AudioModel>> getAllAudio(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AudioModel> audioList = audioService.getAllAudio(page, size);
        return ResponseEntity.ok(audioList);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<AudioModel> getAudioById(@PathVariable String id) {
        Optional<AudioModel> audio = audioService.getAudioById(id);
        return audio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get by name (title)
    @GetMapping("/title/{title}")
    public ResponseEntity<List<AudioModel>> getAudioByName(@PathVariable String title) {
        List<AudioModel> audioList = audioService.getAudioByName(title);
        return ResponseEntity.ok(audioList);
    }

    // Get all by anime ID
    @GetMapping("/anime/{animeId}")
    public ResponseEntity<List<AudioModel>> getAudioByAnime(@PathVariable String animeId) {
        List<AudioModel> audioList = audioService.getAudioByAnime(animeId);
        return ResponseEntity.ok(audioList);
    }

    // Search with multiple criteria
    @GetMapping("/search")
    public ResponseEntity<Page<AudioModel>> searchAudio(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String animeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AudioModel> audioList = audioService.searchAudio(title, type, animeId, page, size);
        return ResponseEntity.ok(audioList);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<AudioModel> updateAudio(@PathVariable String id, @Valid @RequestBody AudioModel updatedAudio) {
        Optional<AudioModel> updated = audioService.updateAudio(id, updatedAudio);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudio(@PathVariable String id) {
        audioService.deleteAudio(id);
        return ResponseEntity.noContent().build();
    }
}