package ru.geekbrains.mytoolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SaveNoteDialogFragment extends DialogFragment {

    public static final String TAG = "MY_DIALOG_FRAGMENT";
    private Navigator navigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = ((MainActivity) context).getNavigator();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.question_to_user)
                .setPositiveButton(R.string.positive_button, (dialogInterface, i) -> navigator.popBackStack())
                .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SaveNoteDialogFragment.this.requireActivity().getBaseContext(),
                                SaveNoteDialogFragment.this.getString(R.string.negative_answer),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
    }
}
