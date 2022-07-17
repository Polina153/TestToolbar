package ru.geekbrains.mytoolbar;

class Note {

    private final String note;
    private final String noteBody;
    private final String date;

    Note(String note, String noteBody, String date) {
        this.note = note;
        this.noteBody = noteBody;
        this.date = date;
    }

    String getNote() {
        return note;
    }

    String getNoteBody() {
        return noteBody;
    }

    String getDate() {
        return date;
    }
}
