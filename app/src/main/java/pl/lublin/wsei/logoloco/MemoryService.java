package pl.lublin.wsei.logoloco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Klasa obsługująca pamięć aplikacji
 */

public class MemoryService {

    /* Początkowa ilość monet */
    public static final int STARTMONEY = 1000;

    /* Koszt podpowiedzi */
    public static final int SHOWONE = 40;
    public static final int SHOWANSWER = 320;

    /* Monety za ukończenie poziomu */
    public static final int PASSLEVELMONEY = 20;

    /* Zmienne przechowujące nr aktualnego pytania w poziomie */
    public static int level_1 = 0;
    public static int level_2 = 0;
    public static int level_3 = 0;
    public static int level_4 = 0;
    public static int level_5 = 0;
    public static int level_6 = 0;
    public static int level_7 = 0;

    /* Zmienna przechowująca podpowiedzi w poziomie */
    public static StringBuilder level_help = new StringBuilder();

    /* Zmienna przechowująca ilosc monet */
    public static int money = PASSLEVELMONEY;

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

        if(sharedPreferences.getString("Level_3", "").equals("")) {
            editor.putString("Level_3", "0");
            editor.putString("Level_3_help", "");
            editor.apply();
            level_3 = 0;
        } else {
            level_3 = Integer.parseInt(sharedPreferences.getString("Level_3", ""));
        }

        if(sharedPreferences.getString("Level_4", "").equals("")) {
            editor.putString("Level_4", "0");
            editor.putString("Level_4_help", "");
            editor.apply();
            level_4 = 0;
        } else {
            level_4 = Integer.parseInt(sharedPreferences.getString("Level_4", ""));
        }

        if(sharedPreferences.getString("Level_5", "").equals("")) {
            editor.putString("Level_5", "0");
            editor.putString("Level_5_help", "");
            editor.apply();
            level_5 = 0;
        } else {
            level_5 = Integer.parseInt(sharedPreferences.getString("Level_5", ""));
        }

        if(sharedPreferences.getString("Level_6", "").equals("")) {
            editor.putString("Level_6", "0");
            editor.putString("Level_6_help", "");
            editor.apply();
            level_6 = 0;
        } else {
            level_6 = Integer.parseInt(sharedPreferences.getString("Level_6", ""));
        }

        if(sharedPreferences.getString("Level_7", "").equals("")) {
            editor.putString("Level_7", "0");
            editor.putString("Level_7_help", "");
            editor.apply();
            level_7 = 0;
        } else {
            level_7 = Integer.parseInt(sharedPreferences.getString("Level_7", ""));
        }

        /* Pobieranie danych dotyczących monet */
        if(sharedPreferences.getString("Money", "").equals("")) {
            editor.putString("Money", String.valueOf(STARTMONEY));
            editor.apply();
            money = STARTMONEY;
        } else {
            money = Integer.parseInt(sharedPreferences.getString("Money", ""));
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
            case 3:
                level_3 = Integer.parseInt(sharedPreferences.getString("Level_3", ""));
                editor.putString("Level_3", String.valueOf(++level_3));
                editor.apply();
                break;
            case 4:
                level_4 = Integer.parseInt(sharedPreferences.getString("Level_4", ""));
                editor.putString("Level_4", String.valueOf(++level_4));
                editor.apply();
                break;
            case 5:
                level_5 = Integer.parseInt(sharedPreferences.getString("Level_5", ""));
                editor.putString("Level_5", String.valueOf(++level_5));
                editor.apply();
                break;
            case 6:
                level_6 = Integer.parseInt(sharedPreferences.getString("Level_6", ""));
                editor.putString("Level_6", String.valueOf(++level_6));
                editor.apply();
                break;
            case 7:
                level_7 = Integer.parseInt(sharedPreferences.getString("Level_7", ""));
                editor.putString("Level_7", String.valueOf(++level_7));
                editor.apply();
                break;
            default:
                break;
        }

        /* Dodanie monet za ukonczenie poziomu */
        money = Integer.parseInt(sharedPreferences.getString("Money", ""));
        money += PASSLEVELMONEY;
        editor.putString("Money", String.valueOf(money));
        editor.apply();
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

        if(showOne) {

            if(money - SHOWONE >= 0) {
                for(int i = 0; i < LevelData.name.length(); i++) {
                    if(level_help.charAt(i) == '0') idx.add(i);
                }
                if(idx.size() > 0) {
                    Collections.shuffle(idx);
                    level_help.setCharAt(idx.get(0), '1');

                    money = money - SHOWONE;

                    editor.putString("Money", String.valueOf(money));
                    editor.putString("Level_" + LevelActivity.currentLevelNumber + "_help", level_help.toString());
                    editor.apply();
                    LevelActivity.money.setText(String.valueOf(money));
                    LevelKeyboard.reloadKeyboard();
                    LevelAnswer.answerBox(context);
                }
            } else {
                Toast.makeText(context, R.string.c_memoryservice_not_enought_money, Toast.LENGTH_SHORT).show();
            }

        } else {

            if(money - SHOWANSWER >= 0) {
                for(int i = 0; i < LevelData.name.length(); i++) {
                    if(level_help.charAt(i) == '0') level_help.setCharAt(i, '1');
                }

                money = money - SHOWANSWER;

                editor.putString("Money", String.valueOf(money));
                editor.putString("Level_" + LevelActivity.currentLevelNumber + "_help", level_help.toString());
                editor.apply();
                LevelActivity.money.setText(String.valueOf(money));
                LevelKeyboard.reloadKeyboard();
                LevelAnswer.answerBox(context);
            } else {
                Toast.makeText(context, R.string.c_memoryservice_not_enought_money, Toast.LENGTH_SHORT).show();
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
        editor.putString("Level_3", "0");
        editor.putString("Level_3_help", "");
        editor.putString("Level_4", "0");
        editor.putString("Level_4_help", "");
        editor.putString("Level_5", "0");
        editor.putString("Level_5_help", "");
        editor.putString("Level_6", "0");
        editor.putString("Level_6_help", "");
        editor.putString("Level_7", "0");
        editor.putString("Level_7_help", "");
        editor.putString("Money", String.valueOf(STARTMONEY));
        editor.putString("Date", "");
        editor.apply();

        level_1 = 0;
        level_2 = 0;
        level_3 = 0;
        level_4 = 0;
        level_5 = 0;
        level_6 = 0;
        level_7 = 0;
        money = STARTMONEY;
        level_help.setLength(0);

        context.startActivity(new Intent(context, MainMenu.class));
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        Toast.makeText(context, R.string.c_memoryservice_factory_reset, Toast.LENGTH_SHORT).show();
    }
}
