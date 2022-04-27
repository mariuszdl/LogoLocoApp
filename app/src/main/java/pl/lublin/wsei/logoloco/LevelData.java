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

    /* Metoda do pobierania danych z aplikacji */
    public static void getData(Context context) {
        getDataFromStrings(context);
        getLogoName(context);
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
            default:
                /* Alert wyświetlany podczas błędu aplikacji */
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    /* Pobieranie nazwy firmy aktualnego pytania */
    private static void getLogoName(Context context) {
        if(x < names.length) {
            /* Pobranie nazwy z tablicy aktualnego poziomu */
            name = names[x];

            /* Generowanie tekstów wyświetlanych w pasku poziomu */
            level = "Poziom " + LevelActivity.currentLevelNumber;
            quest = "Pytanie " + (x + 1) + "/" + names.length;
        } else {
          Toast.makeText(context, "Ukończyłeś poziom " + String.valueOf(LevelActivity.currentLevelNumber), Toast.LENGTH_SHORT).show();
            ((Activity) context).startActivity(new Intent(context, MainMenu.class));
            ((Activity) context).finish();
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    /* Pobranie obrazu logo firmy */
    private static void getLogoImage(Context context) {
        /* Usunięcie białuch znaków z nazwy */
        String logoImageName = name.replaceAll("\\s+","");

        /* Próba pobrania logo z katalogu aktualnego poziomu */
        try {
            /* Towrzenie ścieżki do katalogu aktualnego poziomu */
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
