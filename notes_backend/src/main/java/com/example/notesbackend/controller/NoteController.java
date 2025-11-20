package com.example.notesbackend.controller;

import com.example.notesbackend.dto.NoteRequest;
import com.example.notesbackend.dto.NoteResponse;
import com.example.notesbackend.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API for managing notes.
 */
@RestController
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "CRUD operations for notes")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    // PUBLIC_INTERFACE
    /**
     * Create a new note.
     * @param request NoteRequest with title and content
     * @return NoteResponse representing created note
     */
    @PostMapping
    @Operation(
            summary = "Create note",
            description = "Creates a new note with the provided title and content.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Note created",
                            content = @Content(schema = @Schema(implementation = NoteResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            }
    )
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        NoteResponse created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUBLIC_INTERFACE
    /**
     * Get note by id.
     * @param id the note id
     * @return NoteResponse
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get note by id",
            description = "Retrieves a note by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Note found",
                            content = @Content(schema = @Schema(implementation = NoteResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            }
    )
    public ResponseEntity<NoteResponse> getNoteById(
            @Parameter(description = "Note identifier", example = "1") @PathVariable Long id) {
        NoteResponse note = service.getById(id);
        return ResponseEntity.ok(note);
    }

    // PUBLIC_INTERFACE
    /**
     * List notes with optional pagination.
     * @param page the page number (0-based)
     * @param size the page size (1-200)
     * @return Page of NoteResponse
     */
    @GetMapping
    @Operation(
            summary = "List notes",
            description = "Lists notes with optional pagination parameters.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of notes returned")
            }
    )
    public ResponseEntity<Page<NoteResponse>> listNotes(
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @Parameter(description = "Page size (1-200)", example = "20") @RequestParam(name = "size", required = false, defaultValue = "20") Integer size
    ) {
        Page<NoteResponse> result = service.list(page, size);
        return ResponseEntity.ok(result);
    }

    // PUBLIC_INTERFACE
    /**
     * Update an existing note.
     * @param id the note id
     * @param request NoteRequest with updated title and content
     * @return NoteResponse of updated note
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update note",
            description = "Updates an existing note's title and content.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Note updated",
                            content = @Content(schema = @Schema(implementation = NoteResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            }
    )
    public ResponseEntity<NoteResponse> updateNote(
            @Parameter(description = "Note identifier", example = "1") @PathVariable Long id,
            @Valid @RequestBody NoteRequest request
    ) {
        NoteResponse updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // PUBLIC_INTERFACE
    /**
     * Delete a note by id.
     * @param id the note id
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete note",
            description = "Deletes a note by its identifier.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Note deleted"),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            }
    )
    public ResponseEntity<Void> deleteNote(
            @Parameter(description = "Note identifier", example = "1") @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
