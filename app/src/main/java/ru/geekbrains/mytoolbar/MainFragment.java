package ru.geekbrains.mytoolbar;

import static android.content.Context.MODE_PRIVATE;
import static ru.geekbrains.mytoolbar.DetailsFragment.NOTE_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

//TODO Create separate class to work with SharedPreferences
//TODO Refactor
//TODO Hide keyboard when return back
public class MainFragment extends Fragment {

    public static final String REQUEST_KEY = "requestKey";
    private static final String MY_SHARED_PREF_KEY = "MY_SHARED_PREF_KEY";
    private Navigator navigator;
    private ToolbarCreator toolbarCreator;
    private NotesAdapter notesAdapter;
    private int positionOfClickedElement;
    private SharedPreferences sharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = requireActivity().getPreferences(MODE_PRIVATE);
        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, (requestKey, bundle) -> {
            Note note = bundle.getParcelable(NOTE_KEY);
            try {
                Type type = new TypeToken<ArrayList<Note>>() {
                }.getType();
                String savedNotes = sharedPref.getString(MY_SHARED_PREF_KEY, null);
                if (savedNotes == null) return;
                ArrayList<Note> userNotes = new GsonBuilder().create().fromJson(savedNotes, type);
                userNotes.set(positionOfClickedElement, note);
                String jsonNotes = new GsonBuilder().create().toJson(userNotes);
                sharedPref.edit().putString(MY_SHARED_PREF_KEY, jsonNotes).apply();
                notesAdapter.changeElement(note, positionOfClickedElement);
            } catch (JsonSyntaxException e) {
                Toast.makeText(requireContext(), getString(R.string.mistake), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = ((MainActivity) context).getNavigator();
        toolbarCreator = ((MainActivity) context).getToolbarCreator();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        toolbarCreator.setActionBar(view.findViewById(R.id.my_toolbar), activity);
        setHasOptionsMenu(true);
        createRecyclerView(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));

        recyclerView.addItemDecoration(itemDecoration);

        try {
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();
            String savedNotes = sharedPref.getString(MY_SHARED_PREF_KEY, null);
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
                            positionOfClickedElement = position;
                            navigator.addFragment(DetailsFragment.newInstance(note));
                        }
                    });
        } catch (JsonSyntaxException e) {
            Toast.makeText(requireContext(), getString(R.string.mistake), Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            try {
                Type type = new TypeToken<ArrayList<Note>>() {
                }.getType();
                String sharedPrefString = sharedPref.getString(MY_SHARED_PREF_KEY, null);
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
            } catch (JsonSyntaxException e) {
                Toast.makeText(requireContext(), getString(R.string.mistake), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
}
