package com.mediscreen.report.service.note;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.note.NoteListWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NoteService {

    private final RestTemplate restTemplate;

    private final String requestURI = "http://localhost:8082//note/list/{patientId}";

    public NoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Returns a list of notes by patientId
     * @param patientId
     * @return List<Note>
     */
    public List<Note> getPatientNotes(int patientId) {
        List<Note> noteList;
        NoteListWrapper noteListWrapper = restTemplate.getForObject(requestURI, NoteListWrapper.class);
        noteList = noteListWrapper.getNoteList();
        return noteList;
    }

}
