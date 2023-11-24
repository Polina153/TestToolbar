package ru.geekbrains.mytoolbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.mytoolbar.presenter.Navigator;
import ru.geekbrains.mytoolbar.presenter.ToolbarCreator;
import ru.geekbrains.mytoolbar.view.main_screen.MainFragment;

public class MainActivity extends AppCompatActivity {

    private final ToolbarCreator toolbarCreator = new ToolbarCreator();
    private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigator = new Navigator(getSupportFragmentManager());
        //navigator.addFragment(MainFragment.newInstance());
        if (savedInstanceState == null) {
            navigator.addFragment(MainFragment.newInstance());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ToolbarCreator getToolbarCreator() {
        return toolbarCreator;
    }
}


