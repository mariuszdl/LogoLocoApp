package pl.lublin.wsei.logoloco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Klasa obsługująca poziom aplikacji
 */

public class LevelActivity extends AppCompatActivity {

    /* Zmienna przechowująca numer aktualnie wybranego poziomu */
    public static int currentLevelNumber;

    /* Level Bar */
    ImageView backIcon;
    TextView currentLevel, actualQuest;
    @SuppressLint("StaticFieldLeak")
    public static TextView money;

    /* Wyświetlanie danych logo */
    @SuppressLint("StaticFieldLeak")
    public static ImageView logoImageView;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout answerLayoutOne, answerLayoutTwo, linearLayoutOne, linearLayoutTwo, linearLayoutThree;

    /* Przycisk pomocy */
    @SuppressLint("StaticFieldLeak")
    public static ImageView btnHelp;

    /* Przycisk usuwania znaków */
    @SuppressLint("StaticFieldLeak")
    public static ImageView btnClear;

    /* Przycisk zmiany poziomu */
    @SuppressLint("StaticFieldLeak")
    public static Button btnNextLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        /* Pobranie danych */
        LevelData.getData(LevelActivity.this);

        /* Zdjęcie blokady przed zapisem postępu */
        LevelAnswer.block = false;

        /* Level Bar przypisanie widoków */
        backIcon = findViewById(R.id.backIcon);
        currentLevel = findViewById(R.id.currentLevel);
        actualQuest = findViewById(R.id.actualQuest);
        money = findViewById(R.id.money);

        /* Level Bar przypisanie danych */
        backIcon.setOnClickListener(view -> onBackPressed());
        currentLevel.setText(LevelData.level);
        actualQuest.setText(LevelData.quest);
        money.setText(String.valueOf(MemoryService.money));

        /* Przypisanie widoków poziomu */
        logoImageView = findViewById(R.id.logoImageView);
        btnNextLevel = findViewById(R.id.btnNextLevel);
        btnHelp = findViewById(R.id.btnHelp);
        btnClear = findViewById(R.id.btnClear);
        answerLayoutOne = findViewById(R.id.answerLayoutOne);
        answerLayoutTwo = findViewById(R.id.answerLayoutTwo);
        linearLayoutOne = findViewById(R.id.linearLayoutOne);
        linearLayoutTwo = findViewById(R.id.linearLayoutTwo);
        linearLayoutThree = findViewById(R.id.linearLayoutThree);

        /* Wyswietlanie logo firmy */
        logoImageView.setImageDrawable(LevelData.logoImage);

        /* Generowanie i wyświetlanie klawiatury */
        LevelKeyboard.createKeyboard(LevelActivity.this);

        /* Generowanie i wyświetlanie panelu odpowiedzi */
        LevelAnswer.answerBox(LevelActivity.this);

        btnHelp.setOnClickListener(view -> HelpService.showHelpVariants(LevelActivity.this));

        /* Usuwanie wszystkich znaków z panelu odpowiedzi */
        btnClear.setOnClickListener(view -> {
            for(int i = 0; i < LevelData.name.length(); i++) {
                LevelKeyboard.word[i] = '$';
                LevelKeyboard.reloadKeyboardId(LevelKeyboard.id[i]);
            }
            LevelAnswer.answerBox(this);
        });
        btnNextLevel.setOnClickListener(view -> LevelAnswer.nextLevel(LevelActivity.this));
    }

    /* Zachowanie przycisku powrotu */
    @Override
    public void onBackPressed() {
        /* Zmiana aktywności na MainMenu oraz zakończenie aktywności LevelActivity */
        startActivity(new Intent(LevelActivity.this, MainMenu.class));
        finish();

        /* Nadpisanie domyślnej animacji zmiany aktywności */
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}