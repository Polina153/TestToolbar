package ru.geekbrains.mytoolbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigator = new Navigator(getSupportFragmentManager());
        navigator.addFragment(MyFragment.newInstance());
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
