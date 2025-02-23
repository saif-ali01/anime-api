package com.anime.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "audio")
public class AudioModel {

    @Id
    private String id;

    @NotBlank(message = "Anime ID is required")
    private String animeId; // Links to AnimeModel, auto-set by service

    @NotBlank(message = "Anime name is required")
    private String animeName; // User-provided, used to fetch animeId

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "Dialogue|Song", message = "Type must be 'Dialogue' or 'Song'")
    private String type; // "Dialogue" or "Song"

    @NotBlank(message = "Title is required")
    private String title; // e.g., "Naruto Opening 1" or "Sasuke vs Naruto Dialogue"

    @NotBlank(message = "Audio URL is required")
    @Pattern(regexp = "^https?://.*", message = "Audio URL must be a valid URL")
    private String audioUrl; // URL to the audio file

    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^https?://.*", message = "Image URL must be a valid URL")
    private String imgUrl; // New field: URL to an associated image

    @Min(value = 1, message = "Duration must be positive")
    private int duration; // Duration in seconds

    private String description; // Optional description of the audio

    // Constructors
    public AudioModel() {
    }

    public AudioModel(String id, String animeId, String animeName, String type, String title, 
                      String audioUrl, String imgUrl, int duration, String description) {
        this.id = id;
        this.animeId = animeId;
        this.animeName = animeName;
        this.type = type;
        this.title = title;
        this.audioUrl = audioUrl;
        this.imgUrl = imgUrl;
        this.duration = duration;
        this.description = description;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAnimeId() { return animeId; }
    public void setAnimeId(String animeId) { this.animeId = animeId; }
    public String getAnimeName() { return animeName; }
    public void setAnimeName(String animeName) { this.animeName = animeName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}