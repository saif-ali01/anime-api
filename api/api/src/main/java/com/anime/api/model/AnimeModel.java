package com.anime.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "animes")
public class AnimeModel {

    @Id
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotEmpty(message = "Genres cannot be empty")
    private List<String> genres;

    @Min(value = 1900, message = "Release year must be valid")
    @Max(value = 2026, message = "Release year cannot be too far in the future")
    private int releaseYear;

    @Min(value = 0, message = "Episode count cannot be negative")
    private int episodeCount;

    @NotBlank(message = "Thumbnail URL is required")
    @Pattern(regexp = "^https?://.*", message = "Thumbnail URL must be a valid URL")
    private String thumbnailUrl;

    @NotNull(message = "Status is required")
    private AnimeStatus status;

    @Min(value = 0, message = "Rating cannot be negative")
    @Max(value = 10, message = "Rating cannot be greater than 10")
    private double rating;

    @NotBlank(message = "Studio is required")
    private String studio;

    private List<String> tags;

    private String sourceMaterial;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<String> languageOptions;

    public enum AnimeStatus {
        ONGOING, COMPLETED
    }

    // Constructors
    public AnimeModel() {
    }

    public AnimeModel(String id, String title, String description, List<String> genres, int releaseYear, 
                      int episodeCount, String thumbnailUrl, AnimeStatus status, double rating, 
                      String studio, List<String> tags, String sourceMaterial, LocalDate startDate, 
                      LocalDate endDate, List<String> languageOptions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.episodeCount = episodeCount;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.rating = rating;
        this.studio = studio;
        this.tags = tags;
        this.sourceMaterial = sourceMaterial;
        this.startDate = startDate;
        this.endDate = endDate;
        this.languageOptions = languageOptions;
    }

    // Getters and Setters (omitted for brevity, but include all fields)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public int getEpisodeCount() { return episodeCount; }
    public void setEpisodeCount(int episodeCount) { this.episodeCount = episodeCount; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    public AnimeStatus getStatus() { return status; }
    public void setStatus(AnimeStatus status) { this.status = status; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getStudio() { return studio; }
    public void setStudio(String studio) { this.studio = studio; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public String getSourceMaterial() { return sourceMaterial; }
    public void setSourceMaterial(String sourceMaterial) { this.sourceMaterial = sourceMaterial; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public List<String> getLanguageOptions() { return languageOptions; }
    public void setLanguageOptions(List<String> languageOptions) { this.languageOptions = languageOptions; }
}