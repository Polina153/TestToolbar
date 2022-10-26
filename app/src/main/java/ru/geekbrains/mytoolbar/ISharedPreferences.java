package ru.geekbrains.mytoolbar;

import androidx.annotation.NonNull;

import java.util.ArrayList;

interface ISharedPreferences {

    void saveNote(@NonNull Note note, int position);

    ArrayList<Note> getNotes();

    void saveNewNote(@NonNull Note note);

    void deleteNote(int position);

    void saveNotes(ArrayList<Note> list);
}
