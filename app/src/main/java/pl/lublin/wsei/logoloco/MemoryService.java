package pl.lublin.wsei.logoloco;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Klasa obsługująca pamięć aplikacji
 */

public class MemoryService {

    /* Zmienne przechowujące nr aktualnego pytania w poziomie */
    public static int level_1 = 0;
    public static int level_2 = 0;

    /* Zmienna przechowująca podpowiedzi w poziomie */
    public static StringBuilder level_help = new StringBuilder();

    /* Zmienna przechowująca ilosc monet */
    public static int money = 25;

    /* Zmienne przechowująca datę ostatniego zakręcenia kołem */
    public static String date = "Error";
    public static int availableSpin = 0;

    /* Pobieranie danych z pamięci */
    public static void loadStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LOGO_QUIZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        /* Pobieranie danych dotyczących poziomów */
        if(sharedPreferences.getString("Level_1", "").equals("")) {
            editor.putString("Level_1", "0");
            editor.putString("Level_1_help", "");
            editor.apply();
            level_1 = 0;
        } else {
            level_1 = Integer.parseInt(sharedPreferences.getString("Level_1", ""));
        }

        if(sharedPreferences.getString("Level_2", "").equals("")) {
            editor.putString("Level_2", "0");
            editor.putString("Level_2_help", "");
            editor.apply();
            level_2 = 0;
        } else {
            level_2 = Integer.parseInt(sharedPreferences.getString("Level_2", ""));
        }

        /* Pobieranie danych dotyczących monet */
        if(sharedPreferences.getString("Money", "").equals("")) {
            editor.putString("Money", "100");
            editor.apply();
            money = 100;
        } else {
            money = Integer.parseInt(sharedPreferences.getString("Money", ""));
        }

        /* Pobieranie aktualnej daty */
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        /* Pobieranie danych dotyczących daty */
        if(!sharedPreferences.getString("Date", "").equals(date)) {
            availableSpin += 1;
        }
    }

    /* Zapisywanie postępu w pamięci */
    public static void saveLevelData(Context context, int level) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LOGO_QUIZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (level) {
            case 1:
                level_1 = Integer.parseInt(sharedPreferences.getString("Level_1", ""));
                editor.putString("Level_1", String.valueOf(++level_1));
                editor.apply();
                break;
            case 2:
                level_2 = Integer.parseInt(sharedPreferences.getString("Level_2", ""));
                editor.putString("Level_2", String.valueOf(++level_2));
                editor.apply();
                break;
            default:
                break;
        }

        /* Dodanie monet za ukonczenie poziomu */
        money = Integer.parseInt(sharedPreferences.getString("Money", ""));
        money += 20;
        editor.putString("Money", String.valueOf(money));
        editor.apply();
    }

    /* Zapisanie danych po zakręceniu kołem */
    public static void saveSpinStatus() {

    }

    /* Pobieranie podpowiedzi z pamięci */
    public static void loadHelp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LOGO_QUIZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        level_help.setLength(0);

        if(sharedPreferences.getString("Level_" + LevelActivity.currentLevelNumber + "_help", "").equals("")) {
            for(int i = 0; i < LevelData.name.length(); i++) {
                MemoryService.level_help.append('0');
            }
        } else {
            level_help.append(sharedPreferences.getString("Level_" + LevelActivity.currentLevelNumber + "_help", ""));
        }
        editor.putString("Level_" + LevelActivity.currentLevelNumber + "_help", level_help.toString());
        editor.apply();
    }

    /* Zapisywanie podpowiedzi w pamięci */
    public static void saveHelp(Context context, boolean showOne) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LOGO_QUIZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<Integer> idx = new ArrayList<>();
        idx.clear();

        if(showOne) {

            if(money - 40 >= 0) {
                for(int i = 0; i < LevelData.name.length(); i++) {
                    if(level_help.charAt(i) == '0') idx.add(i);
                }
                if(idx.size() > 0) {
                    Collections.shuffle(idx);
                    level_help.setCharAt(idx.get(0), '1');

                    money = money - 40;

                    editor.putString("Money", String.valueOf(money));
                    editor.putString("Level_" + LevelActivity.currentLevelNumber + "_help", level_help.toString());
                    editor.apply();
                    LevelActivity.money.setText(String.valueOf(money));
                    LevelKeyboard.reloadKeyboard();
                    LevelAnswer.answerBox(context);
                }
            } else {
                Toast.makeText(context, "Nie masz wystaczającej ilości monet", Toast.LENGTH_SHORT).show();
            }

        } else {

            if(money - 320 >= 0) {
                for(int i = 0; i < LevelData.name.length(); i++) {
                    if(level_help.charAt(i) == '0') level_help.setCharAt(i, '1');
                }

                money = money - 320;

                editor.putString("Money", String.valueOf(money));
                editor.putString("Level_" + LevelActivity.currentLevelNumber + "_help", level_help.toString());
                editor.apply();
                LevelActivity.money.setText(String.valueOf(money));
                LevelKeyboard.reloadKeyboard();
                LevelAnswer.answerBox(context);
            } else {
                Toast.makeText(context, "Nie masz wystaczającej ilości monet", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /* Czyszczenie podpowiedzi po ukończeniu poziomu */
    public static void winHelp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LOGO_QUIZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Level_" + LevelActivity.currentLevelNumber + "_help", "");
        editor.apply();
    }

    /* Przywracanie ustawień fabrycznych */
    public static void factoryReset(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LOGO_QUIZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Level_1", "0");
        editor.putString("Level_1_help", "");
        editor.putString("Level_2", "0");
        editor.putString("Level_2_help", "");
        //editor.putString("Money", "100");
        editor.putString("Money", "1000");
        editor.putString("Date", "");
        editor.apply();

        level_1 = 0;
        level_2 = 0;
        //money = 100;
        money = 1000;
        level_help.setLength(0);

        Toast.makeText(context, "Przywrócono ustawienia fabryczne", Toast.LENGTH_SHORT).show();
    }
}
