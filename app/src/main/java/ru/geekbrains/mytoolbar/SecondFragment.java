package ru.geekbrains.mytoolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBar(view);
    }

    private void setActionBar(@NonNull View view) {
        AppCompatActivity activity = ((AppCompatActivity) requireActivity());
        activity.setSupportActionBar(view.findViewById(R.id.second_toolbar));
        setHasOptionsMenu(true);
        ActionBar toolbar = activity.getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).navigation.popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }
}
