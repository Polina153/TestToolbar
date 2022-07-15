package ru.geekbrains.mytoolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    //TODO Create recyclerView
    private Navigator navigator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = ((MainActivity) context).getNavigator();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setActionBar(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            navigator.addFragment(SecondFragment.newInstance());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setActionBar(@NonNull View view) {
        //TODO Move Toolbar creation in separate class
        AppCompatActivity activity = ((AppCompatActivity) requireActivity());
        activity.setSupportActionBar(view.findViewById(R.id.my_toolbar));
        setHasOptionsMenu(true);
    }

    public static Fragment newInstance() {
        return new MyFragment();
    }

}
