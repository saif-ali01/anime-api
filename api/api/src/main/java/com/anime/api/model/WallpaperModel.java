package com.anime.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "wallpapers")
public class WallpaperModel {

    @Id
    private String id;

    @NotBlank(message = "Anime ID is required")
    private String animeId;

    @NotBlank(message = "Anime name is required")
    private String animeName;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^https?://.*", message = "Image URL must be a valid URL")
    private String imageUrl;

    private String resolution; // e.g., "1920x1080" (optional)

    private String description; // Optional description

    // Constructors
    public WallpaperModel() {
    }

    public WallpaperModel(String id, String animeId, String animeName, String title, 
                          String imageUrl, String resolution, String description) {
        this.id = id;
        this.animeId = animeId;
        this.animeName = animeName;
        this.title = title;
        this.imageUrl = imageUrl;
        this.resolution = resolution;
        this.description = description;
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
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}