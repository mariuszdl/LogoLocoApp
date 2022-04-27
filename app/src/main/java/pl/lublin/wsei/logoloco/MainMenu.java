package pl.lublin.wsei.logoloco;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Klasa obsługująca menu główne
 */

public class MainMenu extends AppCompatActivity {

    Button btnLevel1, btnLevel2, btnReset;

    public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /* Przypisanie konkretnych przycisków do zmiennych */
        btnLevel1 = findViewById(R.id.btnLevel1);
        btnLevel2 = findViewById(R.id.btnLevel2);

        /* Zachowanie aplikacji po wciśnięciu przycisku */
        btnLevel1.setOnClickListener(view -> {
            LevelActivity.currentLevelNumber = 1;
            goToLevelActivity();
        });

        btnLevel2.setOnClickListener(view -> {
            LevelActivity.currentLevelNumber = 2;
            goToLevelActivity();
        });
        /*----------------------------------------------*/

        /* Czyszczenie danych aplikacji */
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(view -> {
            MemoryService.factoryReset(MainMenu.this);
        });

//        textView = findViewById(R.id.textView);
//
//        if(MemoryService.availableSpin > 0) {
//            textView.setText(MemoryService.date + "YES");
//        } else {
//            textView.setText(MemoryService.date + "NO");
//        }

    }

    public void goToLevelActivity() {
        startActivity(new Intent(MainMenu.this, LevelActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /* Zachowanie przycisku powrotu */
    @Override
    public void onBackPressed() {
        /* Inicjalizacja dialogu */
        Dialog dialog = new Dialog(this);

        /* Przypisanie widoku wyświetlanego w dialogu */
        dialog.setContentView(R.layout.close_dialog);

        /* Ustawienie przezroczystości tła wyświetlanego dialogu*/
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /* Obsługa przycisku zamknięcia aplikacji */
        dialog.findViewById(R.id.btnClose).setOnClickListener(view -> {
            /* Zamknięcie aplikacji */
            finish();
        });

        /* Obsługa przycisku "anuluj" */
        dialog.findViewById(R.id.btnCancel).setOnClickListener(view -> {
            /* Zamknięcie okna dialogowego */
            dialog.dismiss();
        });

        /* Wyświetlenie okna dialogowego */
        dialog.show();
    }
}