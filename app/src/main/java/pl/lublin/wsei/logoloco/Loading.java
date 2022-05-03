package pl.lublin.wsei.logoloco;

import static pl.lublin.wsei.logoloco.MemoryService.loadStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.VideoView;

/**
 * Klasa obsługująca ekran ładowania aplikacji
 */

public class Loading extends AppCompatActivity {

    /* ProgressBar */
    VideoView mVideoView;
    VideoView mVideoViewLogo;

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

        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoViewLogo = (VideoView) findViewById(R.id.videoViewLogo);

        String uriPath = "android.resource://pl.lublin.wsei.logoloco/" + R.raw.loading_anim;
        String uriPath3 = "android.resource://pl.lublin.wsei.logoloco/" + R.raw.logo_anim;
        Uri uri2 = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri2);
        mVideoView.requestFocus();
        mVideoView.setZOrderOnTop(true);
        mVideoView.start();

        Uri uri3 = Uri.parse(uriPath3);
        mVideoViewLogo.setVideoURI(uri3);
        mVideoViewLogo.requestFocus();
        mVideoViewLogo.setZOrderOnTop(true);
        mVideoViewLogo.start();

        countDownTimer =new CountDownTimer(time,time) {

            @Override
            public void onTick(long millisUntilFinished) {}

            /* Zachowanie aktywności po upływie czasu wyświetlania ekranu ładowania */
            @Override
            public void onFinish() {
                /* Zmiana aktywności na MainMenu oraz zakończenie aktywności Loading */
                startActivity(new Intent(Loading.this, MainMenu.class));
                finish();

//                /* Nadpisanie domyślnej animacji zmiany aktywności */
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };

        /* Uruchomienie timera */
        countDownTimer.start();
    }
}