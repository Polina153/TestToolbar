package ru.geekbrains.mytoolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ToolbarCreator {

    void setActionBar(@NonNull Toolbar toolbar,
                      @NonNull AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);
    }

    void setButtonBack(@Nullable ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
