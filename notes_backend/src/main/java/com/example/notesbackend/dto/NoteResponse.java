package com.example.notesbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

/**
 * Response DTO representing a note.
 */
public class NoteResponse {

    // PUBLIC_INTERFACE
    /** Unique identifier of the note. */
    @Schema(description = "Unique identifier of the note", example = "1")
    private Long id;

    // PUBLIC_INTERFACE
    /** Title of the note. */
    @Schema(description = "Title of the note", example = "Shopping List")
    private String title;

    // PUBLIC_INTERFACE
    /** Content/body of the note. */
    @Schema(description = "Content/body of the note", example = "Buy milk, eggs, and bread")
    private String content;

    // PUBLIC_INTERFACE
    /** Creation timestamp (ISO-8601). */
    @Schema(description = "Creation timestamp (ISO-8601)")
    private OffsetDateTime createdAt;

    // PUBLIC_INTERFACE
    /** Last update timestamp (ISO-8601). */
    @Schema(description = "Last update timestamp (ISO-8601)")
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public NoteResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoteResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteResponse setContent(String content) {
        this.content = content;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public NoteResponse setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public NoteResponse setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
