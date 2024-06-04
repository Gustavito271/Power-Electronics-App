package com.example.appdidatico_tcc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculationsPage extends AppCompatActivity {

    Button convCACC, convCCCC, convCACA, convCCCA;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculations_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeComponents();
        convConfigCACC();
        convConfigCCCC();
        convConfigCCCA();
        backButtonConfig();
    }


    private void initializeComponents() {
        convCACC = findViewById(R.id.btn_convCACC);
        convCCCC = findViewById(R.id.btn_convCCCC);
        convCACA = findViewById(R.id.btn_convCACA);
        convCCCA = findViewById(R.id.btn_convCCCA);
        backButton = findViewById(R.id.voltar_conv);
    }

    private void backButtonConfig() {
        backButton.setOnClickListener(v -> finish());
    }
    private void convConfigCACC() {
        convCACC.setOnClickListener(v -> {
            Intent intent = new Intent(CalculationsPage.this, ConversorCACC.class);
            startActivity(intent);
        });
    }

    private void convConfigCCCC() {
        convCCCC.setOnClickListener(v -> {
            Intent intent = new Intent(CalculationsPage.this, ConversorCCCC.class);
            startActivity(intent);
        });
    }

    private void convConfigCCCA() {
        convCCCA.setOnClickListener(v -> {
            Intent intent = new Intent(CalculationsPage.this, ConversorCCCA.class);
            startActivity(intent);
        });
    }
}