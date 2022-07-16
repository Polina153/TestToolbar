package ru.geekbrains.mytoolbar;

public class Note {

    final String note;
    final String body;
    final String date;


    public Note(String note, String body, String date) {
        this.note = note;
        this.body = body;
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }
}
