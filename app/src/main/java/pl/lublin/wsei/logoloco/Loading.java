package pl.lublin.wsei.logoloco;

import static pl.lublin.wsei.logoloco.MemoryService.loadStatus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Klasa obsługująca ekran ładowania aplikacji
 */

public class Loading extends AppCompatActivity {

    /* Logo */
    ImageView loadingLogo;

    /* ProgressBar */
    VideoView mVideoView;

    /* Timer */
    CountDownTimer countDownTimer;

    /* Długość wyświetlania ekranu ładowania */
    int time = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        /* Pobranie danych z pamięci telefonu */
        loadStatus(Loading.this);

        /* Inicjalizacja widoku logo oraz ładowania */
        loadingLogo = findViewById(R.id.loadingLogo);
        mVideoView = findViewById(R.id.videoView);

        /* Animacja logo */
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        anim.setFillAfter(true);
        loadingLogo.startAnimation(anim);

        String uriPath = "android.resource://pl.lublin.wsei.logoloco/" + R.raw.loading_anim;
        Uri uri2 = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri2);
        mVideoView.requestFocus();
        mVideoView.setZOrderOnTop(true);
        mVideoView.start();

        countDownTimer =new CountDownTimer(time,time) {

            @Override
            public void onTick(long millisUntilFinished) {}

            /* Zachowanie aktywności po upływie czasu wyświetlania ekranu ładowania */
            @Override
            public void onFinish() {
                /* Zmiana aktywności na MainMenu oraz zakończenie aktywności Loading */
                startActivity(new Intent(Loading.this, MainMenu.class));
                finish();

                /* Nadpisanie domyślnej animacji zmiany aktywności */
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };

        /* Uruchomienie timera */
        countDownTimer.start();
    }

    /* Zablokowanie przycisku powrotu */
    @Override
    public void onBackPressed() {}
}