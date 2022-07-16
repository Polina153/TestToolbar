package ru.geekbrains.mytoolbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Navigator navigator;
    private ToolbarCreator toolbarCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigator = new Navigator(getSupportFragmentManager());
        toolbarCreator = new ToolbarCreator();
        navigator.addFragment(MyFragment.newInstance());
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ToolbarCreator getToolbarCreator() {
        return toolbarCreator;
    }
}
