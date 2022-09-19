package ru.geekbrains.mytoolbar;

import static android.content.Context.INPUT_METHOD_SERVICE;
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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    public final static String NOTE_KEY = "NOTE_KEY";
    private static final String NOTE_IS_CLICKED_KEY = "NOTE_IS_CLICKED_KEY";
    private ToolbarCreator toolbarCreator;
    private EditText textOfTheNoteEditText;
    private EditText titleEditText;
    private CheckBox isImportantCheckBox;
    private boolean isKeyboardActive = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarCreator = ((MainActivity) context).getToolbarCreator();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                isKeyboardActive = false;
                showDialogFragment();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
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
        //должно показыввать клавиатуру когда фрагмент в фокусе
        textOfTheNoteEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(textOfTheNoteEditText, InputMethodManager.SHOW_IMPLICIT);

        TextView dateTextView = view.findViewById(R.id.date_of_the_note);
        isImportantCheckBox = view.findViewById(R.id.importance_second_fragment);

        Bundle args = getArguments();
        if (args != null) {
            Note note = args.getParcelable(NOTE_IS_CLICKED_KEY);
            titleEditText.setText(note.getTitle());
            textOfTheNoteEditText.setText(note.getBody());
            dateTextView.setText(note.getDate());
            isImportantCheckBox.setChecked(note.getIsImportant());
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setKeyboardStatus();
            showDialogFragment();
            Bundle result = new Bundle();
            Note note = new Note(titleEditText.getText().toString(), textOfTheNoteEditText.getText().toString(), isImportantCheckBox.isChecked());
            result.putParcelable(NOTE_KEY, note);
            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
            View view = this.requireActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogFragment() {
        SavingDialogFragment dialogFragment = new SavingDialogFragment();
        dialogFragment.show(getChildFragmentManager(), "MY_DIALOG_FRAGMENT");

    }
/*
    private void showAlertDialog() {
        //Fixme FragmentDialog
        //TODO Прочитать про FragmentDialog, какие виды FragmentDialog бывают и объяснить, почему нужно использовать FragmentDialog вместо AlertDialog
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

                .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setKeyboardStatus();
                        Toast.makeText(DetailsFragment.this.requireActivity().getBaseContext(), DetailsFragment.this.getString(R.string.negative_answer), Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCancelListener(dialog -> showSoftKeyboard())
                .setOnDismissListener(dialogInterface -> showSoftKeyboard())
                .show();
    }*/



    void setKeyboardStatus() {
        if (textOfTheNoteEditText.hasFocus() || titleEditText.hasFocus()) {
            isKeyboardActive = true;
        }
    }

    //TODO putParcelable
    public static DetailsFragment newInstance(Note note) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_IS_CLICKED_KEY, note);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }
}
