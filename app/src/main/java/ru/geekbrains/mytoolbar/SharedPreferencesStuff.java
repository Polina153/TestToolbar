package ru.geekbrains.mytoolbar;

import static ru.geekbrains.mytoolbar.MainFragment.notesAdapter;
import static ru.geekbrains.mytoolbar.MainFragment.sharedPref;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public interface SharedPreferencesStuff {
    String MY_SHARED_PREF_KEY = "MY_SHARED_PREF_KEY";
    Type type = new TypeToken<ArrayList<Note>>() {
    }.getType();
    int position = MainFragment.positionOfClickedElement;

    default String gettingSharedPreferences() {
        return sharedPref.getString(MY_SHARED_PREF_KEY, null);
    }

    default void receiveChangesFromDetailsFragment(Note note) {
        String savedNotes = gettingSharedPreferences();
        if (savedNotes == null) return;
        ArrayList<Note> userNotes = new GsonBuilder().create().fromJson(savedNotes, type);
        userNotes.set(MainFragment.positionOfClickedElement, note);
        String jsonNotes = new GsonBuilder().create().toJson(userNotes);
        sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
        notesAdapter.changeElement(note, position);
    }

    default void creatingRecyclerView() {
        String savedNotes = gettingSharedPreferences();
        ArrayList<Note> userNotes;
        if (savedNotes == null) {
            userNotes = new ArrayList<>();
        } else {
            userNotes = new GsonBuilder().create().fromJson(savedNotes, type);
        }
        notesAdapter = new NotesAdapter(userNotes,
                new NotesAdapter.OnMyItemClickListener() {
                    @Override
                    public void onListItemClick(Note note, int position) {
                        String jsonNotes = new GsonBuilder().create().toJson(userNotes);
                        userNotes.set(position, note);
                        sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
                        notesAdapter.changeElement(note, position);
                        MainFragment.navigator.addFragment(DetailsFragment.newInstance(note));
                    }
                });
    }

    default void addingNewNote() {
        String sharedPrefString = gettingSharedPreferences();
        ArrayList<Note> userNotes;
        String jsonNotes;
        if (sharedPrefString == null) {
            userNotes = new ArrayList<>();
            userNotes.add(new Note("Title" + userNotes.size(), "text", false));
            jsonNotes = new GsonBuilder().create().toJson(userNotes);
            sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
        } else {
            userNotes = new GsonBuilder().create().fromJson(sharedPrefString, type);
            userNotes.add(new Note("Title" + userNotes.size(), "text", false));
            jsonNotes = new GsonBuilder().create().toJson(userNotes);
        }
        sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
        notesAdapter.setNewData(userNotes);
    }
}
