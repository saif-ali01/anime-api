package com.anime.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "clips")
public class ClipModel {

    @Id
    private String id;

    @NotBlank(message = "Anime ID is required")
    private String animeId; // Links to AnimeModel, auto-set by service

    @NotBlank(message = "Anime name is required")
    private String animeName; // User-provided, used to fetch animeId

    @NotBlank(message = "Title is required")
    private String title; // e.g., "Naruto vs Sasuke Final Fight"

    @NotBlank(message = "Clip URL is required")
    @Pattern(regexp = "^https?://.*", message = "Clip URL must be a valid URL")
    private String clipUrl; // URL to the video clip

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "Raw|Flowframe|Twixtor", message = "Type must be 'Raw', 'Flowframe', or 'Twixtor'")
    private String type; // Special field: Raw (unedited), Flowframe (frame interpolation), Twixtor (time remapping)

    @Min(value = 1, message = "Duration must be positive")
    private int duration; // Duration in seconds

    private String description; // Optional description of the clip

    @Pattern(regexp = "^https?://.*", message = "Thumbnail URL must be a valid URL")
    private String thumbnailUrl; // Optional thumbnail for the clip

    // Constructors
    public ClipModel() {
    }

    public ClipModel(String id, String animeId, String animeName, String title, String clipUrl, 
                     String type, int duration, String description, String thumbnailUrl) {
        this.id = id;
        this.animeId = animeId;
        this.animeName = animeName;
        this.title = title;
        this.clipUrl = clipUrl;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAnimeId() { return animeId; }
    public void setAnimeId(String animeId) { this.animeId = animeId; }
    public String getAnimeName() { return animeName; }
    public void setAnimeName(String animeName) { this.animeName = animeName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getClipUrl() { return clipUrl; }
    public void setClipUrl(String clipUrl) { this.clipUrl = clipUrl; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
}