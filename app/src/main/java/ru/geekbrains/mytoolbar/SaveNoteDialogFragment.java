package ru.geekbrains.mytoolbar;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SaveNoteDialogFragment extends DialogFragment {

    public static final String TAG = "MY_DIALOG_FRAGMENT";
    private Navigator navigator;
    private boolean isKeyboardActive;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = ((MainActivity) context).getNavigator();
        getFragment();
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
                        DetailsFragment.setKeyboardStatus();
                        Toast.makeText(SaveNoteDialogFragment.this.requireActivity().getBaseContext(), SaveNoteDialogFragment.this.getString(R.string.negative_answer), Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnDismissListener(dialogInterface -> showSoftKeyboard())
                .setOnCancelListener((dialog -> showSoftKeyboard()))
                .create();
    }

    private void showSoftKeyboard() {
        if (isKeyboardActive && getContext() != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(textOfTheNoteEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
