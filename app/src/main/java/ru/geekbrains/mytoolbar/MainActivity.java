package ru.geekbrains.mytoolbar;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public Navigation navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        navigation.addFragment(MyFragment.newInstance());
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Do you really want to go?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> navigation.popBackStack())
                .setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(MainActivity.this, "No!", Toast.LENGTH_SHORT).show())
                .show();
    }

}
