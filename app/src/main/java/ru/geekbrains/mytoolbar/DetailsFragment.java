package ru.geekbrains.mytoolbar;

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

    private final static String BODY_KEY = "BODY_KEY";
    private final static String TITLE_KEY = "TITLE_KEY";
    private final static String DATE_KEY = "DATE_KEY";
    private final static String IMPORTANCE = "IMPORTANCE";
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
        EditText textOfTheNote = view.findViewById(R.id.body_of_note_edit_text);
        EditText title = view.findViewById(R.id.title);
        TextView date = view.findViewById(R.id.date_of_the_note);
        CheckBox importance = view.findViewById(R.id.importance_second_fragment);

        Bundle args = getArguments();
        if (args != null) {

            String title2 = args.getString(TITLE_KEY);
            title.setText(String.valueOf(title2));
            String bodyOfTheNote = args.getString(BODY_KEY);
            textOfTheNote.setText(bodyOfTheNote);
            String date2 = args.getString(DATE_KEY);
            date.setText(date2);
            boolean important = args.getBoolean(IMPORTANCE);
            importance.setChecked(important);
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
                    Bundle result = new Bundle();
                    //    result.putParcelable("bundleKey", Note);
                    getParentFragmentManager().setFragmentResult("requestKey", result);
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
