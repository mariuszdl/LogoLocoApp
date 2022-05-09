package pl.lublin.wsei.logoloco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

/**
 * Klasa służaca do pobierania danych
 */

public class LevelData extends AppCompatActivity {

    /* Nazwa firmy */
    public static String name = "Error";

    /* Zmienna umożliwiająca odwołanie do danych aplikacji */
    public static Resources res;

    /* Tablica przechowująca nazwy firm z wybranego poziomu */
    public static String[] names;

    /* Zmienna zawierajaca aktualne pytanie w wybranym poziomie */
    static int x = 0;

    /* Level Bar */
    public static String level;
    public static String quest;

    /* Obraz logo */
    public static Drawable logoImage;

    /* Czy pytanie jest ostatnie w poziomie */
    public static boolean lastQuestInLevel = false;

    /* Metoda do pobierania danych z aplikacji */
    public static void getData(Context context) {
        getDataFromStrings(context);
        getLogoName();
        getLogoImage(context);
        MemoryService.loadHelp(context);
    }

    /* Pobieranie nazw firm z pliku strings.xml */
    public static void getDataFromStrings(Context context) {
        res = context.getResources();

        switch (LevelActivity.currentLevelNumber) {
            case 1:
                /* Pobranie nazw firm z tablicy */
                names = res.getStringArray(R.array.level_1);

                /* Pobranie numeru aktualnego pytania z pamięci */
                x = MemoryService.level_1;
                break;
            case 2:
                names = res.getStringArray(R.array.level_2);
                x = MemoryService.level_2;
                break;
            case 3:
                names = res.getStringArray(R.array.level_3);
                x = MemoryService.level_3;
                break;
            case 4:
                names = res.getStringArray(R.array.level_4);
                x = MemoryService.level_4;
                break;
            case 5:
                names = res.getStringArray(R.array.level_5);
                x = MemoryService.level_5;
                break;
            case 6:
                names = res.getStringArray(R.array.level_6);
                x = MemoryService.level_6;
                break;
            case 7:
                names = res.getStringArray(R.array.level_7);
                x = MemoryService.level_7;
                break;
            default:
                /* Alert wyświetlany podczas błędu aplikacji */
                Toast.makeText(context, R.string.c_level_data_end_game, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainMenu.class));
                ((Activity) context).finish();
        }
    }

    /* Pobieranie nazwy firmy aktualnego pytania */
    private static void getLogoName() {
        lastQuestInLevel = false;

        /* Pobranie nazwy z tablicy aktualnego poziomu */
        name = names[x];

        /* Generowanie tekstów wyświetlanych w pasku poziomu */
        level = res.getString(R.string.ac_level_level_txt, LevelActivity.currentLevelNumber);
        quest = res.getString(R.string.ac_level_quest_txt, (x + 1), names.length);
        if(x + 1 == names.length) lastQuestInLevel = true;
    }

    /* Pobranie obrazu logo firmy */
    private static void getLogoImage(Context context) {
        /* Usunięcie białuch znaków z nazwy */
        String logoImageName = name.replaceAll("\\s+","");

        /* Próba pobrania logo z katalogu aktualnego poziomu */
        try {
            /* Tworzenie ścieżki do katalogu aktualnego poziomu */
            String lvlDirectory = "Level_" + LevelActivity.currentLevelNumber;

            /* Pobranie danych z folderu assets */
            AssetManager assets = context.getAssets();

            /* Scieżka do logo aktualnej firmy */
            InputStream inputStreamLogo = assets.open(lvlDirectory + "/" + logoImageName + ".png");

            /* Utworzenie flagi przechowującej logo */
            logoImage = Drawable.createFromStream(inputStreamLogo, logoImageName);
        } catch (Exception ignore) {}
    }
}
