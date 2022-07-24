package ru.geekbrains.mytoolbar;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    private Navigator navigator;
    private ToolbarCreator toolbarCreator;

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
        return inflater.inflate(R.layout.my_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        toolbarCreator.setActionBar(view.findViewById(R.id.my_toolbar),activity);
        setHasOptionsMenu(true);
        createRecyclerView(view);
    }

    private void createRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        final ArrayList<Note> userNotes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String index = String.valueOf(i + 1);
            userNotes.add(new Note(index, "text", index, false));
        }
        NotesAdapter notesAdapter = new NotesAdapter(userNotes,
                new NotesAdapter.OnMyItemClickListener() {

                    @Override
                    public void onListItemClick(String title, String noteTextView, String date, boolean isImportant) {
                        navigator.addFragment(SecondFragment.newInstance(title, noteTextView, date, isImportant));

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
            //navigator.addFragment(SecondFragment.newInstance());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Fragment newInstance() {
        return new MyFragment();
    }
}
