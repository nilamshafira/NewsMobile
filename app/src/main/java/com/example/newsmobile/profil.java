package com.example.newsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }

    public void MainActivity(View view) {
        Intent intent = new Intent(profil.this, MainActivity.class);
        startActivity(intent);
    }

    public void feed(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:sitirofiqotullutfiyah22@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hallo, I Want to Ask You!");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

