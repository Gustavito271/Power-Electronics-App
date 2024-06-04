package com.example.appdidatico_tcc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button calculations, theoric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeComponents();
        calculationsConfig();
    }

    private void initializeComponents() {
        calculations = findViewById(R.id.btn_calculo);
        theoric = findViewById(R.id.btn_teorico);
    }
    private void calculationsConfig() {
        calculations.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculationsPage.class);
            startActivity(intent);
        });
    }
}