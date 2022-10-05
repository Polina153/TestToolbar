package ru.geekbrains.mytoolbar;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

class SharedPreferencesImpl implements ISharedPreferences {

    private static final String MY_SHARED_PREF_KEY = "MY_SHARED_PREF_KEY";
    @NonNull
    private final SharedPreferences sharedPref;

    SharedPreferencesImpl(@NonNull SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    @Override
    public void saveNote(@NonNull Note note, int position) throws IllegalStateException {
        try {
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();
            String savedNotes = sharedPref.getString(MY_SHARED_PREF_KEY, null);
            if (savedNotes == null) return;
            ArrayList<Note> userNotes = new GsonBuilder().create().fromJson(savedNotes, type);
            userNotes.set(position, note);
            String jsonNotes = new GsonBuilder().create().toJson(userNotes);
            sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
        } catch (JsonSyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    @NonNull
    public ArrayList<Note> getNotes() throws IllegalStateException {
        ArrayList<Note> userNotes = new ArrayList<>();
        try {
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();
            String savedNotes = sharedPref.getString(MY_SHARED_PREF_KEY, null);
            if (savedNotes != null) {
                userNotes = new GsonBuilder().create().fromJson(savedNotes, type);
            }
        } catch (JsonSyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return userNotes;
    }

    @Override
    public void saveNewNote(@NonNull String title, @NonNull String text) {
        try {
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();
            String savedNotes = sharedPref.getString(MY_SHARED_PREF_KEY, null);
            ArrayList<Note> userNotes = new GsonBuilder().create().fromJson(savedNotes, type);
            if (userNotes == null) {
                userNotes = new ArrayList<>();
            }
            userNotes.add(new Note(title + userNotes.size(), text, false));
            String jsonNotes = new GsonBuilder().create().toJson(userNotes);
            sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
        } catch (JsonSyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
