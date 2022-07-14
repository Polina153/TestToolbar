package ru.geekbrains.mytoolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {

    @Nullable
    private final FragmentManager fragmentManager;

    public Navigation(@NonNull FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void popBackStack() {
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }
}
