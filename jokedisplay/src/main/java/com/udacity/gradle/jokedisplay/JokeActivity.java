package com.udacity.gradle.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "com.udacity.gradle.jokedisplay.extras.EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        if (getIntent() != null) {
            TextView jokeText = findViewById(R.id.joke_text);
            String joke = getIntent().getStringExtra(EXTRA_JOKE);
            if (!TextUtils.isEmpty(joke)) {
                jokeText.setText(joke);
            }
        }
    }
}
