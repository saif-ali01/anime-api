package com.anime.api.controller;

import com.anime.api.model.ClipModel;
import com.anime.api.service.ClipService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clips")
public class ClipController {

    private final ClipService clipService;

    public ClipController(ClipService clipService) {
        this.clipService = clipService;
    }

    // Create
    @PostMapping
    public ResponseEntity<ClipModel> addClip(@Valid @RequestBody ClipModel clip) {
        ClipModel savedClip = clipService.addClip(clip);
        return ResponseEntity.ok(savedClip);
    }

    // Get all clips (paginated)
    @GetMapping
    public ResponseEntity<Page<ClipModel>> getAllClips(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ClipModel> clips = clipService.getAllClips(page, size);
        return ResponseEntity.ok(clips);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClipModel> getClipById(@PathVariable String id) {
        Optional<ClipModel> clip = clipService.getClipById(id);
        return clip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get by name (title)
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ClipModel>> getClipsByName(@PathVariable String title) {
        List<ClipModel> clips = clipService.getClipsByName(title);
        return ResponseEntity.ok(clips);
    }

    // Get all by anime ID
    @GetMapping("/anime/{animeId}")
    public ResponseEntity<List<ClipModel>> getClipsByAnime(@PathVariable String animeId) {
        List<ClipModel> clips = clipService.getClipsByAnime(animeId);
        return ResponseEntity.ok(clips);
    }

    // Search with multiple criteria
    @GetMapping("/search")
    public ResponseEntity<Page<ClipModel>> searchClips(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String animeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ClipModel> clips = clipService.searchClips(title, type, animeId, page, size);
        return ResponseEntity.ok(clips);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ClipModel> updateClip(@PathVariable String id, @Valid @RequestBody ClipModel updatedClip) {
        Optional<ClipModel> updated = clipService.updateClip(id, updatedClip);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClip(@PathVariable String id) {
        clipService.deleteClip(id);
        return ResponseEntity.noContent().build();
    }
}