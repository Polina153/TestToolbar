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
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

//TODO Bring back keyboard if user changes his mind
//TODO OnBackPressedDispatcher
public class DetailsFragment extends Fragment {

    /* public final static String BODY_KEY = "BODY_KEY";
     public final static String TITLE_KEY = "TITLE_KEY";
     public final static String DATE_KEY = "DATE_KEY";
     public final static String IMPORTANCE = "IMPORTANCE";*/
    public final static String NOTE_KEY = "NOTE_KEY";
    private static final String NOTE_IS_CLICKED_KEY = "NOTE_IS_CLICKED_KEY";
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
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true
        ) {
            @Override
            public void handleOnBackPressed() {
                showAlertDialog();
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
        /*должно показыввать клавиатуру когда фрагмент в фокусе
        textOfTheNoteEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(textOfTheNoteEditText, InputMethodManager.SHOW_IMPLICIT);*/
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
            showAlertDialog();
            View view = this.requireActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
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
        /*
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        уже ближе, но надо засунуть в негатив баттн конкретно!

        textOfTheNoteEditText.postDelayed((Runnable) () -> {
            textOfTheNoteEditText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0));
            textOfTheNoteEditText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0));
        }, 200);*/
        /*InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        РАБОТАЕТ РОВНО ЧЕРЕЗ РАЗ!!!*/
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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
