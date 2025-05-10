package com.project.mayin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private Switch themeSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings); // <-- buradan sonra findViewById yapılmalı
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Tema ayarını oku
        sharedPreferences = getSharedPreferences("AppSettingsPrefs", 0);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        // Tema modunu uygula (bu setContentView'ten önce olmalı!)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // 🧩 View'ları tanımla
        themeSwitch = findViewById(R.id.themeSwitch);
        ImageButton homeButtonPage = findViewById(R.id.homeButton);
        Button languageButtonTr = findViewById(R.id.languageButtonTr);
        Button languageButtonEn = findViewById(R.id.languageButtonEn);

        // Switch durumu ayarla
        themeSwitch.setChecked(isDarkMode);

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // SharedPreferences'e kaydet
                sharedPreferences.edit().putBoolean("DarkMode", isChecked).apply();

                // Tema modunu uygula
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                // Aktiviteyi yeniden başlat (ama sadece içerik yenilenmesi için)
                recreate();
            }
        });

        // Ana sayfa ikonuna tıklama
        homeButtonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });

        languageButtonTr.setOnClickListener(v -> setLocale("tr"));
        languageButtonEn.setOnClickListener(v -> setLocale("en"));

    }

    private void backToHomePage() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
        finish();
    }

}