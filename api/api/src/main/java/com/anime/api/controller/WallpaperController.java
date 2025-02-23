package com.anime.api.controller;

import com.anime.api.model.WallpaperModel;
import com.anime.api.service.WallpaperService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallpapers")
public class WallpaperController {

    private final WallpaperService wallpaperService;

    public WallpaperController(WallpaperService wallpaperService) {
        this.wallpaperService = wallpaperService;
    }

    @PostMapping
    public ResponseEntity<WallpaperModel> addWallpaper(@Valid @RequestBody WallpaperModel wallpaper) {
        WallpaperModel savedWallpaper = wallpaperService.addWallpaper(wallpaper);
        return ResponseEntity.ok(savedWallpaper);
    }

    @GetMapping("/anime/{animeId}")
    public ResponseEntity<List<WallpaperModel>> getWallpapersByAnime(@PathVariable String animeId) {
        List<WallpaperModel> wallpapers = wallpaperService.getWallpapersByAnime(animeId);
        return ResponseEntity.ok(wallpapers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WallpaperModel> getWallpaperById(@PathVariable String id) {
        Optional<WallpaperModel> wallpaper = wallpaperService.getWallpaperById(id);
        return wallpaper.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // New search endpoint
    @GetMapping("/search")
    public ResponseEntity<Page<WallpaperModel>> searchWallpapers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String resolution,
            @RequestParam(required = false) String animeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<WallpaperModel> wallpapers = wallpaperService.searchWallpapers(title, resolution, animeId, page, size);
        return ResponseEntity.ok(wallpapers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WallpaperModel> updateWallpaper(@PathVariable String id, @Valid @RequestBody WallpaperModel updatedWallpaper) {
        Optional<WallpaperModel> updated = wallpaperService.updateWallpaper(id, updatedWallpaper);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallpaper(@PathVariable String id) {
        wallpaperService.deleteWallpaper(id);
        return ResponseEntity.noContent().build();
    }
}