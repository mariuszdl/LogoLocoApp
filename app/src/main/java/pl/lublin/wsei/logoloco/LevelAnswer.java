package pl.lublin.wsei.logoloco;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

/**
 * Klasa obsługująca panel odpowiedzi
 */

public class LevelAnswer {

    /* Parametry pól w panelu odpowiedzi */
    protected static final int MARGINESY_POLA_ODP = 4;
    protected static final int WIELKOSC_POLA_ODP = 120;

    /* Dialog wyświetlany po podaniu prawisłowej odpowiedzi */
    protected static Dialog winDialog;

    /* Zabezpieczenie przed wielokrotnym zapisem postępu w pamięci */
    public static boolean block = false;

    /* Tworzenie, odświeżanie i sprawdzanie panelu odpowiedzi */
    public static void answerBox(Context context) {
        /* Pobranie layoutów wyświetlających panel odpowiedzi */
        LinearLayout lla1, lla2;
        lla1 = LevelActivity.answerLayoutOne;
        lla2 = LevelActivity.answerLayoutTwo;

        /* Czyszczenie layoutów wyświetlających panel odpowiedzi */
        lla1.removeAllViews();
        lla2.removeAllViews();

        /* Zmienna przechowująca aktualny layout */
        LinearLayout lla;

        /* Generowanie panelu odpowiedzi */
        for (int i = 0; i < LevelData.name.length(); i++) {
            if(i < 8) {
                lla = lla1;
            } else {
                lla = lla2;
            }

            /* Tworzenie pojedynczego pola w panelu odpowiedzi */
            TextView tv = new TextView(context);

            /* Ustawianie wielkosci pola w panelu odpowiedzi */
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(WIELKOSC_POLA_ODP, WIELKOSC_POLA_ODP);

            /* Ustawianie marginesów */
            p.setMargins(MARGINESY_POLA_ODP,MARGINESY_POLA_ODP,MARGINESY_POLA_ODP,MARGINESY_POLA_ODP);
            tv.setLayoutParams(p);

            /* Ustawianie koloru tekstu */
            tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.char_in_answer_text_color));

            /* Ustawianie rozmiaru tekstu */
            tv.setTextSize(16);

            /* Ustawianie pogrubienia czcionki tekstu */
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            /* Ustawianie tła pola w panelu odpowiedzi */
            tv.setBackgroundResource(R.drawable.answer_box_background);

            /* Ustawienie układu w pozycji centralnej */
            tv.setGravity(Gravity.CENTER);

            /* Przypisanie wartości zmiennej z pętli do zmiennej z */
            int z = i;

            /* Pobranie wartości z tablicy zawierającej aktualnie wprowadzone znaki */
            char let = LevelKeyboard.word[z];

            /* Ustawienie znaku w polu odpowiedzi oraz ukrywanie spacji */
            switch (let) {
                case '$':
                    tv.setText("");
                    break;
                case ' ':
                    tv.setVisibility(View.INVISIBLE);
                    break;
                default:
                    tv.setText(String.valueOf(let));
                    break;
            }

            /* Zachowanie przycisku pola w panelu odpowiedzi */
            tv.setOnClickListener(view -> {
                /* Usuwanie tekstu */
                tv.setText("");

                /* Zmiana znaku w tablicy */
                LevelKeyboard.word[z] = '$';

                /* Przywrócenie przycisku do klawiatury */
                LevelKeyboard.reloadKeyboardId(LevelKeyboard.id[z]);
            });

            if(MemoryService.level_help.charAt(i) == '1') {
                tv.setText(String.valueOf(LevelData.name.charAt(z)));
                LevelKeyboard.word[z] = LevelData.name.charAt(z);
                LevelKeyboard.id[z] = 50;
                tv.setOnClickListener(view -> {});
                tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.good_char_in_answer_text_color));
            }

            /* Dodanie widoku pola odpowiedzi do panelu odpowiedzi */
            lla.addView(tv);
        }

        /* Sprawdzanie odpowiedzi */
        CheckAnswer(context);
    }

    /* Sprawdzanie poprawności podanej odpowiedzi */
    public static void CheckAnswer(Context context) {
        /* Zmienna przechowujaca ilosc pustych pól panelu odpowiedzi */
        int x = 0;

        /* Zliczanie ilości pustych pól panelu odpowiedzi */
        for (int i = 0; i < LevelData.name.length(); i++) {
            if (LevelKeyboard.word[i] == '$') {
                x = 0;
                break;
            }
            x++;
        }

        /* Sprawdzanie czy wszystkie znaki zostały wprowadzone oraz poprawnosci odpowiedzi */
        if (x > 0) {
            if (String.valueOf(LevelKeyboard.word).equals(LevelData.name.toUpperCase())) {
                PassLevel(context);
            } else {
                BadAnswer(context);
            }
        }
    }

    /* Zachowanie aplikacji po podaniu błędnej odpowiedzi */
    public static void BadAnswer(Context context) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.incorrect_shake);
        LevelActivity.logoImageView.startAnimation(anim);
    }

    /* Zachowanie aplikacji po podaniu poprawnej odpowiedzi */
    public static void PassLevel(Context context) {
        /* Zapisanie postępu w pamięci telefonu */
        if(!block) {
            MemoryService.saveLevelData(context, LevelActivity.currentLevelNumber);
            block = true;
        }

        MemoryService.winHelp(context);

        /* Inicjalizacja dialogu */
        winDialog = new Dialog(context);

        /* Przypisanie widoku wyświetlanego w dialogu */
        winDialog.setContentView(R.layout.win_dialog);

        /* Ustawienie przezroczystości tła wyświetlanego dialogu*/
        winDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imv = winDialog.findViewById(R.id.winImageView);
        imv.setImageDrawable(LevelData.logoImage);

        TextView wt = winDialog.findViewById(R.id.winTextView);
        wt.setText(LevelData.name);

        LevelActivity.btnHelp.setVisibility(View.INVISIBLE);
        LevelActivity.btnClear.setVisibility(View.INVISIBLE);
        LevelActivity.btnNextLevel.setVisibility(View.VISIBLE);

        /* Obsługa przycisku kontynuacji */
        winDialog.findViewById(R.id.btnNext).setOnClickListener(view -> {
            nextLevel(context);
        });

        /* Wyświetlenie okna dialogowego */
        winDialog.show();
    }

    public static void nextLevel(Context context) {
        /* "Przeładowanie" aktywności LevelActivity oraz zakończenie poprzedniej aktywności */
        context.startActivity(new Intent(context, LevelActivity.class));
        ((Activity) context).finish();

        /* Nadpisanie domyślnej animacji zmiany aktywności */
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
