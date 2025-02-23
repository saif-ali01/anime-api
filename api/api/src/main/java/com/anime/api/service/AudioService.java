package com.anime.api.service;

import com.anime.api.model.AudioModel;
import com.anime.api.model.AnimeModel;
import com.anime.api.repository.AudioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AudioService {

    private final AudioRepository audioRepository;
    private final AnimeService animeService;

    public AudioService(AudioRepository audioRepository, AnimeService animeService) {
        this.audioRepository = audioRepository;
        this.animeService = animeService;
    }

    // Create
    public AudioModel addAudio(AudioModel audio) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(audio.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + audio.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        audio.setAnimeId(animeId);
        return audioRepository.save(audio);
    }

    // Get all audio (paginated)
    public Page<AudioModel> getAllAudio(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return audioRepository.findAll(pageable);
    }

    // Get by ID
    public Optional<AudioModel> getAudioById(String id) {
        return audioRepository.findById(id);
    }

    // Get by name (title)
    public List<AudioModel> getAudioByName(String title) {
        return audioRepository.findByTitleContaining(title);
    }

    // Get all by anime ID
    public List<AudioModel> getAudioByAnime(String animeId) {
        return audioRepository.findByAnimeId(animeId);
    }

    // Search with multiple criteria
    public Page<AudioModel> searchAudio(String title, String type, String animeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return audioRepository.searchAudio(title != null ? title : "", type != null ? type : "", animeId != null ? animeId : "", pageable);
    }

    // Update
    public Optional<AudioModel> updateAudio(String id, AudioModel updatedAudio) {
        List<AnimeModel> animes = animeService.getAnimesByTitle(updatedAudio.getAnimeName());
        if (animes.isEmpty()) {
            throw new IllegalArgumentException("No anime found with name: " + updatedAudio.getAnimeName());
        }
        String animeId = animes.get(0).getId();
        updatedAudio.setAnimeId(animeId);

        return audioRepository.findById(id)
                .map(existingAudio -> {
                    existingAudio.setAnimeId(updatedAudio.getAnimeId());
                    existingAudio.setAnimeName(updatedAudio.getAnimeName());
                    existingAudio.setType(updatedAudio.getType());
                    existingAudio.setTitle(updatedAudio.getTitle());
                    existingAudio.setAudioUrl(updatedAudio.getAudioUrl());
                    existingAudio.setImgUrl(updatedAudio.getImgUrl());
                    existingAudio.setDuration(updatedAudio.getDuration());
                    existingAudio.setDescription(updatedAudio.getDescription());
                    return audioRepository.save(existingAudio);
                });
    }

    // Delete
    public void deleteAudio(String id) {
        audioRepository.deleteById(id);
    }
}