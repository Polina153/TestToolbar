package ru.geekbrains.mytoolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    //TODO Create recyclerView
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        final ArrayList<Note> userNotes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String index = String.valueOf(i+1);
            userNotes.add(new Note(index, "text", index));
        }
        final NotesAdapter notesAdapter = new NotesAdapter(userNotes);
        //RecyclerView.Adapter notesAdapter;
        recyclerView.setAdapter(notesAdapter);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarCreator.setActionBar(view, ((AppCompatActivity) requireActivity()), R.id.my_toolbar, false);
        setHasOptionsMenu(true);
    }

    public static Fragment newInstance() {
        return new MyFragment();
    }

}
