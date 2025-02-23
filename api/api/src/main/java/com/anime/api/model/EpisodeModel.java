package com.anime.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Document(collection = "episodes")
public class EpisodeModel {

    @Id
    private String id;

    @NotBlank(message = "Anime ID is required")
    private String animeId; // Still required, auto-set by service

    @NotBlank(message = "Anime name is required")
    private String animeName; // New field for user input

    @NotNull(message = "Episode number is required")
    @Min(value = 1, message = "Episode number must be positive")
    private int episodeNumber;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Video URL is required")
    @Pattern(regexp = "^https?://.*", message = "Video URL must be a valid URL")
    private String videoUrl;

    @NotBlank(message = "Type is required")
    private String type; // e.g., "TV", "OVA", "Special"

    @NotNull(message = "Air date is required")
    private LocalDate airDate;

    // Constructors
    public EpisodeModel() {
    }

    public EpisodeModel(String id, String animeId, String animeName, int episodeNumber, String title, 
                        String description, String videoUrl, String type, LocalDate airDate) {
        this.id = id;
        this.animeId = animeId;
        this.animeName = animeName;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.type = type;
        this.airDate = airDate;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAnimeId() { return animeId; }
    public void setAnimeId(String animeId) { this.animeId = animeId; }
    public String getAnimeName() { return animeName; }
    public void setAnimeName(String animeName) { this.animeName = animeName; }
    public int getEpisodeNumber() { return episodeNumber; }
    public void setEpisodeNumber(int episodeNumber) { this.episodeNumber = episodeNumber; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDate getAirDate() { return airDate; }
    public void setAirDate(LocalDate airDate) { this.airDate = airDate; }
}