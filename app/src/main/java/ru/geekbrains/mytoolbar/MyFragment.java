package ru.geekbrains.mytoolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.my_button).setOnClickListener(view1 -> Toast.makeText(requireActivity(), "next Fragment", Toast.LENGTH_SHORT).show());
        setActionBar(view);
    }

    private void setActionBar(@NonNull View view) {
        AppCompatActivity activity = ((AppCompatActivity) requireActivity());
        activity.setSupportActionBar(view.findViewById(R.id.my_toolbar));
        setHasOptionsMenu(true);
        ActionBar toolbar = activity.getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeButtonEnabled(true);
        }
    }

    public static Fragment newInstance() {
        return new MyFragment();
    }
}
