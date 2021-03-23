package com.mediscreen.report.service.note;

import com.mediscreen.report.domain.note.Note;
import com.mediscreen.report.domain.note.NoteListWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NoteService {

    private final RestTemplate restTemplate;

//    private String requestURI = "http://localhost:8082//note/list/";

    public NoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Returns a list of notes by patientId
     * @param patientId
     * @return List<Note>
     */
    public List<Note> getPatientNotes(int patientId) {
        String requestURI = "http://localhost:8082//noteList/" + patientId;
        NoteListWrapper noteListWrapper = restTemplate.getForObject(requestURI, NoteListWrapper.class);
        List<Note> noteList = noteListWrapper.getNoteList();
        return noteList;
    }

}
