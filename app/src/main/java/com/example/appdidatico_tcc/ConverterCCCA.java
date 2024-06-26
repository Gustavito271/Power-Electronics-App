package com.example.appdidatico_tcc;

import static com.example.appdidatico_tcc.Utils.getItem;
import static com.example.appdidatico_tcc.Utils.put;
import static com.example.appdidatico_tcc.Utils.visibilityFields;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ConverterCCCA extends AppCompatActivity {

    Button nextButton;
    ImageButton backButton;
    LinearLayout boxType, boxLoad, boxModulation, boxFormat, boxData;
    Spinner spinnerType, spinnerLoad, spinnerModulation, spinnerFormat, spinnerData;
    TextView ampModulation, initialVoltage, resistance, inductance, frequency;
    EditText editAmpModulation, editInitialVoltage, editResistance, editInductance, editFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_converter_ccca);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeComponents();
        spinnerTypeConfig();
        spinnerLoadConfig();
        spinnerModulationConfig();
        spinnerFormatConfig();
        spinnerDataConfig();
        backButtonConfig();
        nextButtonConfig();
    }

    private void initializeComponents() {
        boxType = findViewById(R.id.box_tipo_CCCA);
        boxModulation = findViewById(R.id.box_mon_modulacao);
        boxLoad = findViewById(R.id.box_cargaCCCA);
        boxFormat = findViewById(R.id.box_tri_ligacao);
        boxData = findViewById(R.id.box_dadosCCCA);

        ampModulation = findViewById(R.id.texto_modulacaoAmplitude);
        initialVoltage = findViewById(R.id.texto_tensaoEntradaCCCA);
        resistance = findViewById(R.id.texto_resistenciaCCCA);
        inductance = findViewById(R.id.texto_indutanciaCCCA);
        frequency = findViewById(R.id.texto_frequenciaCCCA);

        editAmpModulation = findViewById(R.id.editModulacaoAmplitudeCCCA);
        editInitialVoltage = findViewById(R.id.editTensaoEntradaCCCA);
        editResistance = findViewById(R.id.editResistenciaCCCA);
        editInductance = findViewById(R.id.editIndutanciaCCCA);
        editFrequency = findViewById(R.id.editFrequenciaCCCA);

        spinnerType = findViewById(R.id.tipoCCCA);
        spinnerModulation = findViewById(R.id.modulacaoCCCA);
        spinnerLoad = findViewById(R.id.cargaCCCA);
        spinnerFormat = findViewById(R.id.ligacaoCCCA);
        spinnerData = findViewById(R.id.dadosCCCA);

        nextButton = findViewById(R.id.continuar_ccca);
        backButton = findViewById(R.id.voltar_ccca);
    }

    private void spinnerTypeConfig() {
        String[] items = new String[]{getString(R.string.Monophase), getString(R.string.Threephase)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerType.setAdapter(adapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerType.getSelectedItem().toString().equals(getString(R.string.Monophase))) {
                    boxFormat.setVisibility(View.GONE);
                } else {
                    boxFormat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinnerLoadConfig() {
        String[] items = new String[]{getString(R.string.RLoad), getString(R.string.RLLoad)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerLoad.setAdapter(adapter);
    }

    private void spinnerModulationConfig() {
        String[] items = new String[]{getString(R.string.CCCA_unipolar), getString(R.string.CCCA_bipolar)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerModulation.setAdapter(adapter);
    }

    private void spinnerFormatConfig() {
        String[] items = new String[]{getString(R.string.CCCA_star), getString(R.string.CCCA_delta)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerFormat.setAdapter(adapter);
    }

    private void spinnerDataConfig() {
        visibilityFields(ampModulation, editAmpModulation, View.GONE);
        visibilityFields(initialVoltage, editInitialVoltage, View.GONE);
        visibilityFields(resistance, editResistance, View.GONE);
        visibilityFields(frequency, editFrequency, View.GONE);

        String[] select_qualification = new String[] {
            getString(R.string.selecione), getString(R.string.spinner_modulacaoAmplitude), getString(R.string.spinner_tensaoEntrada),
            getString(R.string.spinner_resistencia), getString(R.string.spinner_indutanciaCarga), getString(R.string.spinner_frequenciaReferencia)
        };

        EditText[] editTexts = new EditText[] {
            editAmpModulation, editAmpModulation, editInitialVoltage, editResistance, editInductance, editFrequency
        };

        TextView[] textViews = new TextView[] {
            ampModulation, ampModulation, initialVoltage, resistance, inductance, frequency
        };

        ArrayList<SpinnerCheckbox> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            SpinnerCheckbox stateVO = new SpinnerCheckbox();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            stateVO.setEditText(editTexts[i]);
            stateVO.setTextView(textViews[i]);
            listVOs.add(stateVO);
        }

        CustomAdapter myAdapter = new CustomAdapter(ConverterCCCA.this, 0, listVOs);
        spinnerData.setAdapter(myAdapter);
    }

    private void nextButtonConfig() {
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConverterCCCA.this, ResultCCCA.class);
            put(intent, getString(R.string.key_amplitudeModulation), editAmpModulation);
            put(intent, getString(R.string.key_initialVoltage), editInitialVoltage);
            put(intent, getString(R.string.key_resistance), editResistance);
            put(intent, getString(R.string.key_loadIndutance), editInductance);
            put(intent, getString(R.string.key_frequency), editFrequency);
            intent.putExtra(getString(R.string.key_type), getItem(spinnerType));
            intent.putExtra(getString(R.string.key_format), getItem(spinnerFormat));

            startActivity(intent);
        });
    }

    private void backButtonConfig() {
        backButton.setOnClickListener(v -> finish());
    }
}