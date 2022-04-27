package pl.lublin.wsei.logoloco;

import static pl.lublin.wsei.logoloco.MemoryService.loadStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

/**
 * Klasa obsługująca ekran ładowania aplikacji
 */

public class Loading extends AppCompatActivity {

    /* ProgressBar */
    ProgressBar progressBar;

    /* Timer */
    CountDownTimer countDownTimer;

    /* Długość wyświetlania ekranu ładowania */
    int time = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        /* Pobranie danych z pamięci telefonu */
        loadStatus(Loading.this);

        progressBar = findViewById(R.id.progressBar);
        countDownTimer =new CountDownTimer(time,100) {

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
}