package ru.geekbrains.mytoolbar;

import static ru.geekbrains.mytoolbar.MainFragment.REQUEST_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    public final static String BODY_KEY = "BODY_KEY";
    public final static String TITLE_KEY = "TITLE_KEY";
    public final static String DATE_KEY = "DATE_KEY";
    public final static String IMPORTANCE = "IMPORTANCE";
    public final static String NOTE_KEY = "NOTE_KEY";
    private Navigator navigator;
    private ToolbarCreator toolbarCreator;
    private EditText textOfTheNoteEditText;
    private EditText titleEditText;
    private CheckBox isImportantCheckBox;

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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        toolbarCreator.setActionBar(
                view.findViewById(R.id.second_toolbar),
                activity);
        toolbarCreator.setButtonBack(activity.getSupportActionBar());
        textOfTheNoteEditText = view.findViewById(R.id.body_of_note_edit_text);
        titleEditText = view.findViewById(R.id.title);
        TextView dateTextView = view.findViewById(R.id.date_of_the_note);
        isImportantCheckBox = view.findViewById(R.id.importance_second_fragment);

        Bundle args = getArguments();
        if (args != null) {
            titleEditText.setText(args.getString(TITLE_KEY));
            textOfTheNoteEditText.setText(args.getString(BODY_KEY));
            dateTextView.setText(args.getString(DATE_KEY));
            isImportantCheckBox.setChecked(args.getBoolean(IMPORTANCE));
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showAlertDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.question_to_user)
                .setPositiveButton(R.string.positive_button, (dialogInterface, i) ->
                {
                    Bundle result = new Bundle();/*
                    result.putString(TITLE_KEY, titleEditText.getText().toString());
                    result.putString(BODY_KEY, textOfTheNoteEditText.getText().toString());
                    result.putString(DATE_KEY, dateTextView.getText().toString());
                    result.putBoolean(IMPORTANCE, isImportantCheckBox.isChecked());*/
                    Note note = new Note(titleEditText.getText().toString(), textOfTheNoteEditText.getText().toString(), isImportantCheckBox.isChecked());
                    result.putParcelable(NOTE_KEY, note);
                    getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
                    navigator.popBackStack();
                })

                .setNegativeButton(R.string.negative_button, (dialogInterface, i) ->
                        Toast.makeText(requireActivity().getBaseContext(), getString(R.string.negative_answer), Toast.LENGTH_SHORT).show())
                .show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static DetailsFragment newInstance(String title, String noteTextView, String date, boolean isImportant) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, title);
        bundle.putString(BODY_KEY, noteTextView);
        bundle.putString(DATE_KEY, date);
        bundle.putBoolean(IMPORTANCE, isImportant);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }
}
