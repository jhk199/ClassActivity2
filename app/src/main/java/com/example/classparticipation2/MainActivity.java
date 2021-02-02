package com.example.classparticipation2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button button_go;
    private TextInputEditText text_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_go = findViewById(R.id.button_go);
        text_input = findViewById(R.id.text_input);
    }

    public void launchNextActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String city = text_input.getText().toString();
        intent.putExtra("city name", city);
        startActivityForResult(intent, 1);

    }
}