package ru.geekbrains.mytoolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {

    private final static String POSITION_KEY = "POSITION_KEY";
    private Navigator navigator;
    private ToolbarCreator toolbarCreator;
    private static Bundle args;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = ((MainActivity) context).getNavigator();
        toolbarCreator = ((MainActivity) context).getToolbarCreator();
        args = new Bundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_fragment, container, false);
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
        Bundle args = getArguments();
        int position = args.getInt(String.valueOf(args));
        textOfTheNote.setText(position);
        args.putString(POSITION_KEY, String.valueOf(position));
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
                        navigator.popBackStack())
                .setNegativeButton(R.string.negative_button, (dialogInterface, i) ->
                        Toast.makeText(requireActivity().getBaseContext(), getString(R.string.negative_answer), Toast.LENGTH_SHORT).show())//FIXME
                .show();
    }

    public static SecondFragment newInstance(int listItemPosition) {
        SecondFragment secondFragment = new SecondFragment();
        int bedaBedaIOgor4enie = args.getInt(String.valueOf(listItemPosition));
        secondFragment.setArguments(args);
        return secondFragment;
    }
}
