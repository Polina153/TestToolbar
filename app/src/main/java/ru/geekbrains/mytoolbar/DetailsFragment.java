package ru.geekbrains.mytoolbar;

import static ru.geekbrains.mytoolbar.MainFragment.REQUEST_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
            View view = this.requireActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.question_to_user)
                .setPositiveButton(R.string.positive_button, (dialogInterface, i) ->
                {
                    Bundle result = new Bundle();
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

    //TODO putParcelable
    public static DetailsFragment newInstance(Note note) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(NOTE_IS_CLICKED_KEY, note);
        bundle.putString(TITLE_KEY, note.getTitle());
        bundle.putString(BODY_KEY, note.getBody());
        bundle.putString(DATE_KEY, note.getDate());
        bundle.putBoolean(IMPORTANCE, note.getIsImportant());
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }
}
