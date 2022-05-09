package pl.lublin.wsei.logoloco;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Klasa obsługująca menu główne
 */

public class MainMenu extends AppCompatActivity {

    public static final int LEVELCOUNT = 7;
    public static int questCounter = 0;
    TextView [] textView;
    ConstraintLayout[] btnLevel;
    TextView [] txtLevel;
    ImageView [] lockLevel;
    ImageView [] unlockLevel;
    TextView [] progressLevel;
    ImageView btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        textView = new TextView[LEVELCOUNT];
        btnLevel = new ConstraintLayout[LEVELCOUNT];
        txtLevel = new TextView[LEVELCOUNT];
        lockLevel = new ImageView[LEVELCOUNT];
        unlockLevel = new ImageView[LEVELCOUNT];
        progressLevel = new TextView[LEVELCOUNT];

        /* Przypisanie konkretnych przycisków do zmiennych */
        textView[0] = findViewById(R.id.textView1);
        textView[1] = findViewById(R.id.textView2);
        textView[2] = findViewById(R.id.textView3);
        textView[3] = findViewById(R.id.textView4);
        textView[4] = findViewById(R.id.textView5);
        textView[5] = findViewById(R.id.textView6);
        textView[6] = findViewById(R.id.textView7);

        btnLevel[0] = findViewById(R.id.btnLevel1);
        btnLevel[1] = findViewById(R.id.btnLevel2);
        btnLevel[2] = findViewById(R.id.btnLevel3);
        btnLevel[3] = findViewById(R.id.btnLevel4);
        btnLevel[4] = findViewById(R.id.btnLevel5);
        btnLevel[5] = findViewById(R.id.btnLevel6);
        btnLevel[6] = findViewById(R.id.btnLevel7);

        txtLevel[0] = findViewById(R.id.txtLevel1);
        txtLevel[1] = findViewById(R.id.txtLevel2);
        txtLevel[2] = findViewById(R.id.txtLevel3);
        txtLevel[3] = findViewById(R.id.txtLevel4);
        txtLevel[4] = findViewById(R.id.txtLevel5);
        txtLevel[5] = findViewById(R.id.txtLevel6);
        txtLevel[6] = findViewById(R.id.txtLevel7);

        lockLevel[0] = findViewById(R.id.lockLevel1);
        lockLevel[1] = findViewById(R.id.lockLevel2);
        lockLevel[2] = findViewById(R.id.lockLevel3);
        lockLevel[3] = findViewById(R.id.lockLevel4);
        lockLevel[4] = findViewById(R.id.lockLevel5);
        lockLevel[5] = findViewById(R.id.lockLevel6);
        lockLevel[6] = findViewById(R.id.lockLevel7);

        unlockLevel[0] = findViewById(R.id.unlockLevel1);
        unlockLevel[1] = findViewById(R.id.unlockLevel2);
        unlockLevel[2] = findViewById(R.id.unlockLevel3);
        unlockLevel[3] = findViewById(R.id.unlockLevel4);
        unlockLevel[4] = findViewById(R.id.unlockLevel5);
        unlockLevel[5] = findViewById(R.id.unlockLevel6);
        unlockLevel[6] = findViewById(R.id.unlockLevel7);

        progressLevel[0] = findViewById(R.id.progressLevel1);
        progressLevel[1] = findViewById(R.id.progressLevel2);
        progressLevel[2] = findViewById(R.id.progressLevel3);
        progressLevel[3] = findViewById(R.id.progressLevel4);
        progressLevel[4] = findViewById(R.id.progressLevel5);
        progressLevel[5] = findViewById(R.id.progressLevel6);
        progressLevel[6] = findViewById(R.id.progressLevel7);

        setLevelStatus();

        /* Czyszczenie danych aplikacji */
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(view -> MemoryService.factoryReset(MainMenu.this));
    }

    @SuppressLint("SetTextI18n")
    public void setLevelStatus() {
        questCounter = MemoryService.level_1 + MemoryService.level_2 + MemoryService.level_3 +
                MemoryService.level_4 + MemoryService.level_5 + MemoryService.level_6 + MemoryService.level_7;

        textView[0].setText(getString(R.string.ac_mainmenu_lvl_nr, 1));
        textView[1].setText(getString(R.string.ac_mainmenu_lvl_nr, 2));
        textView[2].setText(getString(R.string.ac_mainmenu_lvl_nr, 3));
        textView[3].setText(getString(R.string.ac_mainmenu_lvl_nr, 4));
        textView[4].setText(getString(R.string.ac_mainmenu_lvl_nr, 5));
        textView[5].setText(getString(R.string.ac_mainmenu_lvl_nr, 6));
        textView[6].setText(getString(R.string.ac_mainmenu_lvl_nr, 7));

        btnLevel[0].setOnClickListener(view -> {
            btnLevel[0].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
            LevelActivity.currentLevelNumber = 1;
            goToLevelActivity();
        });
        txtLevel[0].setText(MemoryService.level_1 + "/10");
        lockLevel[0].setVisibility(View.INVISIBLE);
        if(MemoryService.level_1 == 0) {
            unlockLevel[0].setVisibility(View.VISIBLE);
            progressLevel[0].setVisibility(View.INVISIBLE);
        } else {
            unlockLevel[0].setVisibility(View.INVISIBLE);
            progressLevel[0].setText((MemoryService.level_1 * 10) + "%");
            progressLevel[0].setVisibility(View.VISIBLE);
        }


        if(questCounter >= 9) {
            lockLevel[1].setVisibility(View.INVISIBLE);
            txtLevel[1].setText(MemoryService.level_2 + "/10");
            btnLevel[1].setOnClickListener(view -> {
                btnLevel[1].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                LevelActivity.currentLevelNumber = 2;
                goToLevelActivity();
            });
            if(MemoryService.level_2 == 0) {
                unlockLevel[1].setVisibility(View.VISIBLE);
                progressLevel[1].setVisibility(View.INVISIBLE);
            } else {
                unlockLevel[1].setVisibility(View.INVISIBLE);
                progressLevel[1].setText((MemoryService.level_2 * 10) + "%");
                progressLevel[1].setVisibility(View.VISIBLE);
            }
        } else {
            lockLevel[1].setVisibility(View.VISIBLE);
            txtLevel[1].setText(getString(R.string.ac_mainmenu_need_to_unlock, (9 - questCounter)));
            btnLevel[1].setOnClickListener(view -> {
                btnLevel[1].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_need_to_unlock_toast, (9 - questCounter)), Toast.LENGTH_SHORT).show();
            });
        }

        if(questCounter >= 18) {
            lockLevel[2].setVisibility(View.INVISIBLE);
            txtLevel[2].setText(MemoryService.level_3 + "/10");
            btnLevel[2].setOnClickListener(view -> {
                btnLevel[2].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                LevelActivity.currentLevelNumber = 3;
                goToLevelActivity();
            });
            if(MemoryService.level_3 == 0) {
                unlockLevel[2].setVisibility(View.VISIBLE);
                progressLevel[2].setVisibility(View.INVISIBLE);
            } else {
                unlockLevel[2].setVisibility(View.INVISIBLE);
                progressLevel[2].setText((MemoryService.level_3 * 10) + "%");
                progressLevel[2].setVisibility(View.VISIBLE);
            }
        } else {
            lockLevel[2].setVisibility(View.VISIBLE);
            txtLevel[2].setText(getString(R.string.ac_mainmenu_need_to_unlock, (18 - questCounter)));
            btnLevel[2].setOnClickListener(view -> {
                btnLevel[2].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_need_to_unlock_toast, (18 - questCounter)), Toast.LENGTH_SHORT).show();
            });
        }


        if(questCounter >= 27) {
            lockLevel[3].setVisibility(View.INVISIBLE);
            txtLevel[3].setText(MemoryService.level_4 + "/10");
            btnLevel[3].setOnClickListener(view -> {
                btnLevel[3].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                LevelActivity.currentLevelNumber = 4;
                goToLevelActivity();
            });
            if(MemoryService.level_4 == 0) {
                unlockLevel[3].setVisibility(View.VISIBLE);
                progressLevel[3].setVisibility(View.INVISIBLE);
            } else {
                unlockLevel[3].setVisibility(View.INVISIBLE);
                progressLevel[3].setText((MemoryService.level_4 * 10) + "%");
                progressLevel[3].setVisibility(View.VISIBLE);
            }
        } else {
            lockLevel[3].setVisibility(View.VISIBLE);
            txtLevel[3].setText(getString(R.string.ac_mainmenu_need_to_unlock, (27 - questCounter)));
            btnLevel[3].setOnClickListener(view -> {
                btnLevel[3].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_need_to_unlock_toast, (27 - questCounter)), Toast.LENGTH_SHORT).show();
            });
        }

        if(questCounter >= 36) {
            lockLevel[4].setVisibility(View.INVISIBLE);
            txtLevel[4].setText(MemoryService.level_5 + "/10");
            btnLevel[4].setOnClickListener(view -> {
                btnLevel[4].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                LevelActivity.currentLevelNumber = 5;
                goToLevelActivity();
            });
            if(MemoryService.level_5 == 0) {
                unlockLevel[4].setVisibility(View.VISIBLE);
                progressLevel[4].setVisibility(View.INVISIBLE);
            } else {
                unlockLevel[4].setVisibility(View.INVISIBLE);
                progressLevel[4].setText((MemoryService.level_5 * 10) + "%");
                progressLevel[4].setVisibility(View.VISIBLE);
            }
        } else {
            lockLevel[4].setVisibility(View.VISIBLE);
            txtLevel[4].setText(getString(R.string.ac_mainmenu_need_to_unlock, (36 - questCounter)));
            btnLevel[4].setOnClickListener(view -> {
                btnLevel[4].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_need_to_unlock_toast, (36 - questCounter)), Toast.LENGTH_SHORT).show();
            });
        }


        if(questCounter >= 45) {
            lockLevel[5].setVisibility(View.INVISIBLE);
            txtLevel[5].setText(MemoryService.level_6 + "/10");
            btnLevel[5].setOnClickListener(view -> {
                btnLevel[5].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                LevelActivity.currentLevelNumber = 6;
                goToLevelActivity();
            });
            if(MemoryService.level_6 == 0) {
                unlockLevel[5].setVisibility(View.VISIBLE);
                progressLevel[5].setVisibility(View.INVISIBLE);
            } else {
                unlockLevel[5].setVisibility(View.INVISIBLE);
                progressLevel[5].setText((MemoryService.level_6 * 10) + "%");
                progressLevel[5].setVisibility(View.VISIBLE);
            }
        } else {
            lockLevel[5].setVisibility(View.VISIBLE);
            txtLevel[5].setText(getString(R.string.ac_mainmenu_need_to_unlock, (45 - questCounter)));
            btnLevel[5].setOnClickListener(view -> {
                btnLevel[5].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_need_to_unlock_toast, (45 - questCounter)), Toast.LENGTH_SHORT).show();
            });
        }

        if(questCounter >= 54) {
            lockLevel[6].setVisibility(View.INVISIBLE);
            txtLevel[6].setText(MemoryService.level_7 + "/10");
            btnLevel[6].setOnClickListener(view -> {
                btnLevel[6].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                LevelActivity.currentLevelNumber = 7;
                goToLevelActivity();
            });
            if(MemoryService.level_7 == 0) {
                unlockLevel[6].setVisibility(View.VISIBLE);
                progressLevel[6].setVisibility(View.INVISIBLE);
            } else {
                unlockLevel[6].setVisibility(View.INVISIBLE);
                progressLevel[6].setText((MemoryService.level_7 * 10) + "%");
                progressLevel[6].setVisibility(View.VISIBLE);
            }
        } else {
            lockLevel[6].setVisibility(View.VISIBLE);
            txtLevel[6].setText(getString(R.string.ac_mainmenu_need_to_unlock, (54 - questCounter)));
            btnLevel[6].setOnClickListener(view -> {
                btnLevel[6].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_need_to_unlock_toast, (54 - questCounter)), Toast.LENGTH_SHORT).show();
            });
        }


        if(MemoryService.level_1 == 10) {
            btnLevel[0].setOnClickListener(view -> {
                btnLevel[0].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 1), Toast.LENGTH_SHORT).show();
            });
        }

        if(MemoryService.level_2 == 10) {
            btnLevel[1].setOnClickListener(view -> {
                btnLevel[1].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 2), Toast.LENGTH_SHORT).show();
            });
        }

        if(MemoryService.level_3 == 10) {
            btnLevel[2].setOnClickListener(view -> {
                btnLevel[2].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 3), Toast.LENGTH_SHORT).show();
            });
        }

        if(MemoryService.level_4 == 10) {
            btnLevel[3].setOnClickListener(view -> {
                btnLevel[3].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 4), Toast.LENGTH_SHORT).show();
            });
        }

        if(MemoryService.level_5 == 10) {
            btnLevel[4].setOnClickListener(view -> {
                btnLevel[4].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 5), Toast.LENGTH_SHORT).show();
            });
        }

        if(MemoryService.level_6 == 10) {
            btnLevel[5].setOnClickListener(view -> {
                btnLevel[5].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 6), Toast.LENGTH_SHORT).show();
            });
        }

        if(MemoryService.level_7 == 10) {
            btnLevel[6].setOnClickListener(view -> {
                btnLevel[6].startAnimation(AnimationUtils.loadAnimation(this, R.anim.onclick));
                Toast.makeText(this, getString(R.string.ac_mainmenu_finish_level, 7), Toast.LENGTH_SHORT).show();
            });
        }
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