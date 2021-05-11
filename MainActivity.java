package com.example.speako;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tx;
    private Button btn2;
    ArrayList<String> ar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        tx = findViewById(R.id.textView);
        btn2 = findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access for recognition of voice
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                // To get all default lang from mobile OS
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                // To select our mobile default lang
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                // To show some mess for spekaer to start
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now....");

                // Request voice conversion throught OS with pro

                startActivityForResult(intent, 1);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Send info using intent
                Intent share = new Intent(Intent.ACTION_SEND);

                // Attach text data with intent
                share.putExtra(Intent.EXTRA_TEXT, ar.get(0));

                // Define the datatype
                share.setType("text/plain");

                // To share intent

                startActivity(Intent.createChooser(share, "Share via..."));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ar = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            tx.setText(ar.get(0));

            if (ar.get(0).equals("open camera")) {
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(cam);
            }
        }
    }
}