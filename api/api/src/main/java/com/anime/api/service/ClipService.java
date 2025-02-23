package com.anime.api.service;

import com.anime.api.model.ClipModel;
import com.anime.api.model.AnimeModel;
import com.anime.api.repository.ClipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClipService {

    private final ClipRepository clipRepository;
    private final AnimeService animeService;

    public ClipService(ClipRepository clipRepository, AnimeService animeService) {
        this.clipRepository = clipRepository;
        this.animeService = animeService;
    }

    // Create
    public ClipModel addClip(ClipModel clip) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(clip.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + clip.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        clip.setAnimeId(animeId);
        return clipRepository.save(clip);
    }

    // Get all clips (paginated)
    public Page<ClipModel> getAllClips(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clipRepository.findAll(pageable);
    }

    // Get by ID
    public Optional<ClipModel> getClipById(String id) {
        return clipRepository.findById(id);
    }

    // Get by name (title)
    public List<ClipModel> getClipsByName(String title) {
        return clipRepository.findByTitleContaining(title);
    }

    // Get all by anime ID
    public List<ClipModel> getClipsByAnime(String animeId) {
        return clipRepository.findByAnimeId(animeId);
    }

    // Search with multiple criteria
    public Page<ClipModel> searchClips(String title, String type, String animeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clipRepository.searchClips(
            title != null ? title : "", 
            type != null ? type : "", 
            animeId != null ? animeId : "", 
            pageable
        );
    }

    // Update
    public Optional<ClipModel> updateClip(String id, ClipModel updatedClip) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(updatedClip.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + updatedClip.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        updatedClip.setAnimeId(animeId);

        return clipRepository.findById(id)
                .map(existingClip -> {
                    existingClip.setAnimeId(updatedClip.getAnimeId());
                    existingClip.setAnimeName(updatedClip.getAnimeName());
                    existingClip.setTitle(updatedClip.getTitle());
                    existingClip.setClipUrl(updatedClip.getClipUrl());
                    existingClip.setType(updatedClip.getType());
                    existingClip.setDuration(updatedClip.getDuration());
                    existingClip.setDescription(updatedClip.getDescription());
                    existingClip.setThumbnailUrl(updatedClip.getThumbnailUrl());
                    return clipRepository.save(existingClip);
                });
    }

    // Delete
    public void deleteClip(String id) {
        clipRepository.deleteById(id);
    }
}