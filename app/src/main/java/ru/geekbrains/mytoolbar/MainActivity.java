package ru.geekbrains.mytoolbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final ToolbarCreator toolbarCreator = new ToolbarCreator();
    private Navigator navigator;
//    private SharedPreferences sharedPref = null;
//    public static final String SHARED_PREF_KEY = "SHARED_PREF_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        sharedPref = getPreferences(MODE_PRIVATE);
        final ArrayList<Note> userNotes = new ArrayList<>();
        String savedNotes = sharedPref.getString(SHARED_PREF_KEY, null);
        if (savedNotes == null) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Type type = new TypeToken<ArrayList<Note>>() {
                }.getType();
                notesAdapter.setNewData(new GsonBuilder().create().fromJson(savedNotes, type));
            } catch (JsonSyntaxException e) {
                Toast.makeText(this, "Ошибка трансформации", Toast.LENGTH_SHORT).show();
            }

            */
/*UserNote userNote = new GsonBuilder().create().fromJson(savedNotes, UserNote.class);
            userNotes.add(userNote);
            notesAdapter.setNewData(userNotes);*//*

        }

*/
/*        if (savedInstanceState == null) {
            navigator = new Navigator(getSupportFragmentManager());
            navigator.addFragment(MyFragment.newInstance());
        }
    }*/
        navigator = new Navigator(getSupportFragmentManager());
        navigator.addFragment(MainFragment.newInstance());
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ToolbarCreator getToolbarCreator() {
        return toolbarCreator;
    }
}


