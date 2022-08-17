package ru.geekbrains.mytoolbar;

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
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private static final String MY_ARRAY_LIST_KEY = "MY_ARRAY_LIST";
    private Navigator navigator;
    private ToolbarCreator toolbarCreator;
    ArrayList<Note> userNotes = new ArrayList<>();
    private NotesAdapter notesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Note result = bundle.getParcelable("bundleKey");

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
        if (savedInstanceState != null) {
            //      userNotes = (ArrayList<Note>) savedInstanceState.getParcellableArrayList(MY_ARRAY_LIST_KEY);
            userNotes = savedInstanceState.getParcelableArrayList(MY_ARRAY_LIST_KEY);
        }
        setHasOptionsMenu(true);
        createRecyclerView(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createRecyclerView(@NonNull View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);/*
        for (int i = 0; i < 1; i++) {
            userNotes.add(new Note("Title" + i, "text", false));
        }*/
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));

        recyclerView.addItemDecoration(itemDecoration);

        notesAdapter = new NotesAdapter(userNotes,
                (title, noteTextView, date, isImportant) -> navigator.addFragment(DetailsFragment.newInstance(title, noteTextView, date, isImportant)));
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