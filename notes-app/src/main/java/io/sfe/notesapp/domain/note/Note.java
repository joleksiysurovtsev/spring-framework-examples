package io.sfe.notesapp.domain.note;

import java.util.Objects;

public class Note {

    private final String text;

    public Note(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "Note{" +
            "text='" + text + '\'' +
            '}';
    }
}
