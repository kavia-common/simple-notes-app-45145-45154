package com.example.notesbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating/updating a note.
 */
public class NoteRequest {

    // PUBLIC_INTERFACE
    /** Title of the note (1-200 characters). */
    @Schema(description = "Title of the note", example = "Shopping List", maxLength = 200)
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    // PUBLIC_INTERFACE
    /** Content/body of the note. */
    @Schema(description = "Content/body of the note", example = "Buy milk, eggs, and bread")
    @NotBlank(message = "Content is required")
    private String content;

    public NoteRequest() {}

    public NoteRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public NoteRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoteRequest setContent(String content) {
        this.content = content;
        return this;
    }
}
