package ru.geekbrains.mytoolbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ToolbarCreator {

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


}
