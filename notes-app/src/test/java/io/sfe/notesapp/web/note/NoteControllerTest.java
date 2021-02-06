package io.sfe.notesapp.web.note;

import io.sfe.notesapp.domain.note.Note;
import io.sfe.notesapp.domain.note.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class NoteControllerTest {

    @Autowired
    @MockBean
    private NoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("All notes request")
    void all_notes_request() throws Exception {
        when(noteService.findAll()).thenReturn(
            List.of(Note.of(1, "1"))
        );

        mockMvc.perform(get("/notes"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("note/notes"))
            .andExpect(model().attribute("notes", List.of(NoteDto.of(1, "1"))));
    }

    @Test
    @DisplayName("Show create note page")
    void show_create_note_page() throws Exception {
        mockMvc.perform(get("/notes/create-note"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("note/create-note"))
            .andExpect(model().size(0));
    }

    @Test
    @DisplayName("Save note failed for missing 'text' argument")
    void save_note_failed_for_missing_text_argument() throws Exception {
        mockMvc.perform(post("/notes"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Note saved")
    void note_saved() throws Exception {
        mockMvc.perform(
            post("/notes")
            .param("text", "text")
        ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/notes"));

        verify(noteService).save("text");
    }
}
