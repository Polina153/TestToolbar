package ru.geekbrains.mytoolbar;

import static ru.geekbrains.mytoolbar.DetailsFragment.BODY_KEY;
import static ru.geekbrains.mytoolbar.DetailsFragment.DATE_KEY;
import static ru.geekbrains.mytoolbar.DetailsFragment.IMPORTANCE;
import static ru.geekbrains.mytoolbar.DetailsFragment.TITLE_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//TODO Receive class Note instead of different properties
//TODO Save all data to SharedPreferences

public class MainFragment extends Fragment {

    public static final String REQUEST_KEY = "requestKey";
    private static final String MY_ARRAY_LIST_KEY = "MY_ARRAY_LIST";
    private Navigator navigator;
    private ToolbarCreator toolbarCreator;
    private ArrayList<Note> userNotes = new ArrayList<>();
    private NotesAdapter notesAdapter;
    private int positionOfClickedElement;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, (requestKey, bundle) -> {
            String title = bundle.getString(TITLE_KEY);
            String bodyOfTheNote = bundle.getString(BODY_KEY);
            String date = bundle.getString(DATE_KEY);
            boolean isImportant = bundle.getBoolean(IMPORTANCE);
            notesAdapter.changeElement(new Note(title, bodyOfTheNote, isImportant), positionOfClickedElement);
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
        if (savedInstanceState != null) {
            userNotes = savedInstanceState.getParcelableArrayList(MY_ARRAY_LIST_KEY);
        }
        setHasOptionsMenu(true);
        createRecyclerView(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));

        recyclerView.addItemDecoration(itemDecoration);

        notesAdapter = new NotesAdapter(userNotes,
                new NotesAdapter.OnMyItemClickListener() {
                    @Override
                    public void onListItemClick(String title, String noteTextView, String date, boolean isImportant, int position) {
                        positionOfClickedElement = position;
                        navigator.addFragment(DetailsFragment.newInstance(title, noteTextView, date, isImportant));
                    }
                });
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
            userNotes.add(new Note("Title" + userNotes.size(), "text", false));
            notesAdapter.setNewData(userNotes);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(MY_ARRAY_LIST_KEY, userNotes);
        super.onSaveInstanceState(outState);
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
}
