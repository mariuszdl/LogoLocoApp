package pl.lublin.wsei.logoloco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Klasa obsługująca klawiaturę
 */

public class LevelKeyboard {

    /* Parametry przycisków z literami */
    protected static final int ILOSC_KLAWISZY_KLAWIATURY = 21;
    protected static final int MARGINESY_KLAWIATURY = 6;
    protected static final int WIELKOSC_KLAWISZA_KLAWIATURY = 52;

    /* Lista przechowująca litery klawiatury */
    protected static ArrayList<Character> chars;

    /* Tablica przycisków klawiatury */
    protected static Button[] letterButtons;

    /* Tablica przechowująca wprowadzone znaki */
    protected static char[] word;

    /* Tablica przechowująca id przycisków wprowadzonych znaków */
    protected static int[] id;

    /* Index pierwszego pustego pola w panelu odpowiedzi */
    protected static int current = 0;

    @SuppressLint("StaticFieldLeak")
    protected static LinearLayout ll, ll1, ll2, ll3;


    /* Generowanie liter do wyświetlenia na klawiaturze */
    private static void letters() {
        LevelData.name = LevelData.name.toUpperCase();

        word = new char[LevelData.name.length()];
        for(int i = 0; i < LevelData.name.length(); i++) {
            word[i] = '$';
        }
        id = new int[LevelData.name.length()];

        int k = 0;
        chars = new ArrayList<>();
        for (char c : LevelData.name.toCharArray()) {
            if (c != ' ') {
                chars.add(c);
            } else {
                chars.add('A');
                word[k] = ' ';
            }
            k++;
        }

        Random r = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < (ILOSC_KLAWISZY_KLAWIATURY - LevelData.name.length()); i++) {
            chars.add(alphabet.charAt(r.nextInt(alphabet.length())));
        }

        Collections.shuffle(chars);
        letterButtons = new Button[chars.size()];
    }

    /* Tworzenie klawiatury ze znakami do wprowadzania */
    public static void createKeyboard(Context context) {
        /* Generowanie liter */
        letters();

        /* Pobranie layoutów wyświetlających panel odpowiedzi */
        ll1 = LevelActivity.linearLayoutOne;
        ll2 = LevelActivity.linearLayoutTwo;
        ll3 = LevelActivity.linearLayoutThree;

        /* Czyszczenie layoutów wyświetlających klawiaturę */
        ll1.removeAllViews();
        ll2.removeAllViews();
        ll3.removeAllViews();

        /* Wielkość klawisza w dp */
        int sizeLetter = (int) (WIELKOSC_KLAWISZA_KLAWIATURY * context.getResources().getDisplayMetrics().density);

        /* Generowanie klawiatury */
        for (int i = 0; i < ILOSC_KLAWISZY_KLAWIATURY; i++) {
            if(i < (ILOSC_KLAWISZY_KLAWIATURY / 3)) {
                ll = ll1;
            } else if (i < (ILOSC_KLAWISZY_KLAWIATURY / 3 * 2)) {
                ll = ll2;
            } else {
                ll = ll3;
            }

            /* Tworzenie przycisku */
            Button letter = new Button(context);

            /* Ustawianie wielkosci przycisków na klawiaturze */
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(sizeLetter, sizeLetter);

            /* Ustawianie marginesów */
            p.setMargins(MARGINESY_KLAWIATURY, MARGINESY_KLAWIATURY, MARGINESY_KLAWIATURY, MARGINESY_KLAWIATURY);
            letter.setLayoutParams(p);

            /* Ustawianie koloru tekstu */
            letter.setTextColor(ContextCompat.getColor(context, R.color.char_on_keyboard_text_color));

            /* Ustawianie id przycisku */
            letter.setId(i);

            /* Przypisanie pobranego znaku do zmiennej y */
            char y = chars.get(i);

            /* Przypisanie wartości zmiennej z pętli do zmiennej z */
            int z = i;

            /* Wyświetlanie litery na przycisku */
            letter.setText(String.valueOf(y));

            /* Ustawianie tła litery na klawiaturze */
            letter.setBackgroundResource(R.drawable.char_button_background);

            /* Zachowanie przycisku litery na klawiaturze */
            letter.setOnClickListener(view -> {
                /* Poszukiwanie indexu pierwszego pustego pola w panelu odpowiedzi */
                for (int i1 = 0; i1 < LevelData.name.length(); i1++) {
                    if (word[i1] == '$') {
                        /* Przypisanie indexu pierwszego pustego pola do zmiennej current */
                        current = i1;
                        break;
                    }
                }
                try {
                    /* Przypisanie litery z przycisku do tablicy przechowującej wprowadzone znaki */
                    word[current] = y;

                    /* Przypisanie id przycisku do tablicy przechowującej id */
                    id[current] = z;

                    /* Przypisanie zmiennej current wartosci spoza zakresu */
                    current = 100;

                    /* Ukrywanie wciśniętego przycisku */
                    letter.setVisibility(View.INVISIBLE);

                    /* Odświeżanie panelu odpowiedzi */
                    LevelAnswer.answerBox(context);
                } catch (Exception ignored) {}
            });

            /* Dodanie przycisku do tablicy */
            letterButtons[i] = letter;

            /* Dodanie przycisku do klawiatury */
            ll.addView(letter);
        }
        reloadKeyboard();
    }

    /* Odświeżanie klawiatury po wciśnięciu przycisku */
    public static void reloadKeyboardId(int id) {
        /* Czyszczenie paneli z widoków */
        ll1.removeAllViews();
        ll2.removeAllViews();
        ll3.removeAllViews();

        /* Generowanie klawiatury */
        for (int i = 0; i < ILOSC_KLAWISZY_KLAWIATURY; i++) {
            if(i < (ILOSC_KLAWISZY_KLAWIATURY / 3)) {
                ll = ll1;
            } else if (i < (ILOSC_KLAWISZY_KLAWIATURY / 3 * 2)) {
                ll = ll2;
            } else {
                ll = ll3;
            }

            /* Pobieranie przycisków z tablicy */
            Button letter = letterButtons[i];

            /* Przywrócenie widoczności przycisku */
            if(i == id) {
                letterButtons[i].setVisibility(View.VISIBLE);
            }

            /* Dodanie przycisku do klawiatury */
            ll.addView(letter);
        }
    }

    /* Odświeżanie klawiatury po użyciu podpowiedzi */
    public static void reloadKeyboard() {
        /* Czyszczenie paneli z widoków */
        ll1.removeAllViews();
        ll2.removeAllViews();
        ll3.removeAllViews();

        ArrayList<Character> l = new ArrayList<>();
        String k = MemoryService.level_help.toString();
        int x = 0;

        for (char c : k.toCharArray()) {
            if (c == '1') {
                l.add(LevelData.name.charAt(x));
            }
            x++;
        }

        /* Generowanie klawiatury */
        for (int i = 0; i < ILOSC_KLAWISZY_KLAWIATURY; i++) {
            if(i < (ILOSC_KLAWISZY_KLAWIATURY / 3)) {
                ll = ll1;
            } else if (i < (ILOSC_KLAWISZY_KLAWIATURY / 3 * 2)) {
                ll = ll2;
            } else {
                ll = ll3;
            }

            /* Pobieranie przycisków z tablicy */
            Button letter = letterButtons[i];

            for (int j = 0; j < l.size(); j++) {
                if(letterButtons[i].getText().charAt(0) == l.get(j)) {
                    letterButtons[i].setVisibility(View.INVISIBLE);
                    l.remove(j);
                }
            }

            /* Dodanie przycisku do klawiatury */
            ll.addView(letter);
        }
    }
}