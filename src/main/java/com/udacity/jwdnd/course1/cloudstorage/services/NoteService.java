package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    public List<Note> getNotesByUserId(Integer userId) { return noteMapper.getNotes(userId); }

    public void addNote(NoteForm noteForm, Integer userId) {
        Note newNote = new Note();

        newNote.setNoteTitle(noteForm.getNoteTitle());
        newNote.setNoteDescription(noteForm.getNoteDescription());
        newNote.setUserId(userId);
        noteMapper.insertNote(newNote);
    }

    public void updateNote(NoteForm noteForm) {
        Note updateNote = new Note();

        updateNote.setNoteId(noteForm.getNoteId());
        updateNote.setNoteTitle(noteForm.getNoteTitle());
        updateNote.setNoteDescription(noteForm.getNoteDescription());
        noteMapper.updateNoteById(updateNote);
    }

    public void deleteNote(Integer noteId) { noteMapper.deleteNoteById(noteId); }
}
