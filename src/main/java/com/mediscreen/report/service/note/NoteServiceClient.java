package com.mediscreen.report.service.note;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.note.NoteListWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NoteServiceClient {

    private final RestTemplate restTemplate;

    public NoteServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Returns a list of notes by patientId
     * @param patientId
     * @return List<Note>
     */
    public List<Note> getPatientNotes(int patientId) {
        String requestURI = "http://notes:8082/noteList/" + patientId;
        NoteListWrapper noteListWrapper = restTemplate.getForObject(requestURI, NoteListWrapper.class);
        List<Note> noteList = noteListWrapper.getNoteList();
        return noteList;
    }

}
