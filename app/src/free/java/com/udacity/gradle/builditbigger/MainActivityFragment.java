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
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.jokedisplay.JokeActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeRetriever {

    private ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;
    private String mJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        assert getContext() != null;
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startJokeActivity();
            }
        });

        loadInterstitialAd();

        Button jokeButton = root.findViewById(R.id.joke_btn);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJoke();
            }
        });

        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(GONE);
        return root;
    }

    private void getJoke() {
        progressBar.setVisibility(VISIBLE);
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this);
        endpointsAsyncTask.execute();

    }

    @Override
    public void onRetrieveJoke(String jokeResult) {
        progressBar.setVisibility(GONE);
        mJoke = jokeResult;
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            startJokeActivity();
        } else {
            startJokeActivity();
        }
    }

    private void startJokeActivity() {
        if (mJoke != null) {
            assert getActivity() != null;
            Intent intent = new Intent(getActivity(), JokeActivity.class);
            intent.putExtra(JokeActivity.EXTRA_JOKE, mJoke);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
