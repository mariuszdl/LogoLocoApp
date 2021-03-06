package pl.lublin.wsei.logoloco;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

/**
 * Klasa obsługująca panel podpowiedzi
 */

public class HelpService {

    protected static Dialog helpDialog;

    /* Wyświetlenie dialogu podpowiedzi */
    public static void showHelpVariants(Context context) {

        /* Inicjalizacja dialogu */
        helpDialog = new Dialog(context);

        /* Przypisanie widoku wyświetlanego w dialogu */
        helpDialog.setContentView(R.layout.help_dialog);

        /* Ustawienie przezroczystości tła wyświetlanego dialogu*/
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView showOneTextView = helpDialog.findViewById(R.id.showOneTextView);
        TextView showAnswerTextView = helpDialog.findViewById(R.id.showAnswerTextView);

        showOneTextView.setText(String.valueOf(MemoryService.SHOWONE));
        showAnswerTextView.setText(String.valueOf(MemoryService.SHOWANSWER));

        /* Obsługa przycisku kupna jednej litery */
        helpDialog.findViewById(R.id.showOneHelp).setOnClickListener(view -> {
            MemoryService.saveHelp(context, true);

            /* Zamknięcie okna dialogowego */
            helpDialog.dismiss();
        });

        /* Obsługa przycisku kupna odkrycia odpowiedzi */
        helpDialog.findViewById(R.id.showAnswerHelp).setOnClickListener(view -> {
            MemoryService.saveHelp(context, false);

            /* Zamknięcie okna dialogowego */
            helpDialog.dismiss();
        });

        /* Wyświetlenie okna dialogowego */
        helpDialog.show();
    }
}
