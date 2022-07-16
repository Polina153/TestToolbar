package ru.geekbrains.mytoolbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ToolbarCreator {

    public static ToolbarCreator toolbarCreator = new ToolbarCreator();

    public ToolbarCreator(){

    }

    void setActionBar(@NonNull View view,
                      AppCompatActivity activity,
                      int layoutItem,
                      Boolean hasButtonBack)
                     // Boolean hasAddButton
     {
        //TODO Move Toolbar creation in separate class
        //activity = ((AppCompatActivity) requireActivity());
        activity.setSupportActionBar(view.findViewById(layoutItem));
        ActionBar toolbar = activity.getSupportActionBar();
        if (hasButtonBack && toolbar != null){
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeButtonEnabled(true);
        }
   //     if (hasAddButton && toolbar != null){
   //     }
    }

/*

    private void setActionBar(@NonNull View view) {
        AppCompatActivity activity = ((AppCompatActivity) requireActivity());
        activity.setSupportActionBar(view.findViewById(R.id.second_toolbar));
        ActionBar toolbar = activity.getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeButtonEnabled(true);
        }
        setHasOptionsMenu(true);
    }
*/
}
