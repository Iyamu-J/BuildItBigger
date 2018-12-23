package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.jokedisplay.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeRetriever {

    ProgressBar progressBar;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button jokeButton = root.findViewById(R.id.joke_btn);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJoke();
            }
        });
        return root;
    }

    private void getJoke() {
        progressBar.setVisibility(View.VISIBLE);
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this);
        endpointsAsyncTask.execute();

    }

    @Override
    public void onRetrieveJoke(String jokeResult) {
        if (jokeResult != null) {
            Intent intent = new Intent(getActivity(), JokeActivity.class);
            intent.putExtra(JokeActivity.EXTRA_JOKE, jokeResult);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
        }
    }
}
