package ru.geekbrains.mytoolbar;

class Note {

    private final String note;
    private final String body;
    private final String date;

    Note(String note, String body, String date) {
        this.note = note;
        this.body = body;
        this.date = date;
    }

    String getNote() {
        return note;
    }

    String getBody() {
        return body;
    }

    String getDate() {
        return date;
    }
}
