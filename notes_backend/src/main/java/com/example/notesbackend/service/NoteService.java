package com.example.notesbackend.service;

import com.example.notesbackend.dto.NoteRequest;
import com.example.notesbackend.dto.NoteResponse;
import com.example.notesbackend.model.Note;
import com.example.notesbackend.repository.NoteRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service encapsulating business logic for notes.
 */
@Service
public class NoteService {

    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    // PUBLIC_INTERFACE
    /**
     * Creates a new note from the given request.
     * @param request NoteRequest containing title and content.
     * @return NoteResponse representing the created note.
     */
    public NoteResponse create(NoteRequest request) {
        Note note = new Note(request.getTitle(), request.getContent());
        Note saved = repository.save(note);
        return toDto(saved);
    }

    // PUBLIC_INTERFACE
    /**
     * Retrieves a note by id.
     * @param id Note identifier.
     * @return NoteResponse
     * @throws NoSuchElementException if not found.
     */
    public NoteResponse getById(Long id) {
        Note note = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found with id: " + id));
        return toDto(note);
    }

    // PUBLIC_INTERFACE
    /**
     * Lists notes with pagination support.
     * @param page page number (0-based)
     * @param size page size
     * @return Page of NoteResponse
     */
    public Page<NoteResponse> list(int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(Math.min(size, 200), 1), Sort.by(Sort.Direction.DESC, "updatedAt"));
        return repository.findAll(pageable).map(this::toDto);
    }

    // PUBLIC_INTERFACE
    /**
     * Updates an existing note.
     * @param id note id
     * @param request NoteRequest with updated fields
     * @return updated NoteResponse
     * @throws NoSuchElementException if not found.
     */
    public NoteResponse update(Long id, NoteRequest request) {
        Note note = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Note not found with id: " + id));
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note saved = repository.save(note);
        return toDto(saved);
    }

    // PUBLIC_INTERFACE
    /**
     * Deletes a note by id.
     * @param id note id
     * @throws NoSuchElementException if not found.
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Note not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private NoteResponse toDto(Note note) {
        return new NoteResponse()
                .setId(note.getId())
                .setTitle(note.getTitle())
                .setContent(note.getContent())
                .setCreatedAt(note.getCreatedAt())
                .setUpdatedAt(note.getUpdatedAt());
    }
}
