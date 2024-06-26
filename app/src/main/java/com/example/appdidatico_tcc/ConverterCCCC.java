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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ConverterCCCC extends AppCompatActivity {

    TextView initialVoltage, finalVoltage;
    TextView resistance, loadIndutance;
    TextView cycle;
    TextView filterIndutance, filterCapacitance, frequency;
    TextView currentOscilation, voltageOscilation;
    EditText editInitialVoltage, editFinalVoltage;
    EditText editCycle;
    EditText editResistance, editLoadIndutance;
    EditText editFilterIndutance, editFilterCapacitance, editFrequency;
    EditText editCurrentOscilation, editVoltageOscilation;
    Spinner spinnerLoad, spinnerData;
    Button nextButton;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_converter_cccc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeComponents();
        loadSpinnerConfig();
        dataSpinnerConfig();
        nextButtonConfig();
        backButtonConfig();
    }

    private void initializeComponents() {
        backButton = findViewById(R.id.voltar_cccc);
        nextButton = findViewById(R.id.continuar_cccc);

        initialVoltage = findViewById(R.id.texto_vin_CCCC);
        finalVoltage = findViewById(R.id.texto_vout_CCCC);
        resistance = findViewById(R.id.texto_res_CCCC);
        cycle = findViewById(R.id.texto_ciclo_CCCC);
        loadIndutance = findViewById(R.id.texto_indCarga_CCCC);
        filterIndutance = findViewById(R.id.texto_indFiltro_CCCC);
        filterCapacitance = findViewById(R.id.texto_cap_CCCC);
        frequency = findViewById(R.id.texto_freq_CCCC);
        currentOscilation = findViewById(R.id.texto_deltaCorrente_CCCC);
        voltageOscilation = findViewById(R.id.texto_deltaTensao_CCCC);

        editInitialVoltage = findViewById(R.id.editTensaoCCCC);
        editFinalVoltage = findViewById(R.id.editTensaoSaidaCCCC);
        editResistance = findViewById(R.id.editResCCCC);
        editCycle = findViewById(R.id.editCicloCCC);
        editLoadIndutance = findViewById(R.id.editIndCargaCCCC);
        editFilterIndutance = findViewById(R.id.editIndFiltroCCCC);
        editFilterCapacitance = findViewById(R.id.editCapCCCC);
        editFrequency = findViewById(R.id.editFreqCCCC);
        editCurrentOscilation = findViewById(R.id.editDeltaCorrenteCCCC);
        editVoltageOscilation = findViewById(R.id.editDeltaTensaoCCCC);

        spinnerLoad = findViewById(R.id.carga_CCCC);
        spinnerData = findViewById(R.id.dados_CCCC);
    }

    private void loadSpinnerConfig() {
        String[] items = new String[]{getString(R.string.CCCC_RLoad), getString(R.string.RLLoad), getString(R.string.CCCC_LCFilter)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerLoad.setAdapter(adapter);

        spinnerLoad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataSpinnerConfig();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void dataSpinnerConfig() {
        boolean isR = getItem(spinnerLoad).equals(getString(R.string.CCCC_RLoad));
        boolean isRL = getItem(spinnerLoad).equals(getString(R.string.RLLoad));

        visibilityFields(initialVoltage, editInitialVoltage, View.GONE);
        visibilityFields(finalVoltage, editFinalVoltage, View.GONE);
        visibilityFields(resistance, editResistance, View.GONE);
        visibilityFields(cycle, editCycle, View.GONE);
        visibilityFields(loadIndutance, editLoadIndutance, View.GONE);
        visibilityFields(filterIndutance, editFilterIndutance, View.GONE);
        visibilityFields(filterCapacitance, editFilterCapacitance, View.GONE);
        visibilityFields(frequency, editFrequency, View.GONE);

        String[] select_qualification = isR ?
            new String[] { getString(R.string.selecione), getString(R.string.spinner_tensaoEntrada), getString(R.string.spinner_TensaoSaida),
                           getString(R.string.spinner_resistencia), getString(R.string.spinner_cicloTrabalho)} :
          isRL ?
            new String[] {getString(R.string.selecione), getString(R.string.spinner_tensaoEntrada), getString(R.string.spinner_TensaoSaida),
                          getString(R.string.spinner_resistencia), getString(R.string.spinner_cicloTrabalho), getString(R.string.spinner_indutanciaCarga),
                          getString(R.string.spinner_deltaIo), getString(R.string.spinner_frequencia_chaveamento)}
          :
            new String[] {getString(R.string.selecione), getString(R.string.spinner_tensaoEntrada), getString(R.string.spinner_TensaoSaida),
                          getString(R.string.spinner_resistencia), getString(R.string.spinner_cicloTrabalho), getString(R.string.spinner_indutanciaFiltro),
                          getString(R.string.spinner_capacitanciaFiltro), getString(R.string.spinner_deltaVo), getString(R.string.spinner_deltaIo),
                          getString(R.string.spinner_frequencia_chaveamento)};

        EditText[] editTexts = isR ?
            new EditText[] {editInitialVoltage, editInitialVoltage, editFinalVoltage, editResistance,
                editCycle} :
          isRL ?
            new EditText[] {editInitialVoltage, editInitialVoltage, editFinalVoltage, editResistance,
                editCycle, editLoadIndutance, editCurrentOscilation, editFrequency}
          :
            new EditText[] {editInitialVoltage, editInitialVoltage, editFinalVoltage, editResistance,
                editCycle, editFilterIndutance, editFilterCapacitance, editVoltageOscilation, editCurrentOscilation,
                editFrequency};

        TextView[] textViews = isR ?
            new TextView[] {initialVoltage, initialVoltage, finalVoltage, resistance, cycle} :
          isRL ?
            new TextView[] {initialVoltage, initialVoltage, finalVoltage, resistance, cycle, loadIndutance,
                currentOscilation, frequency}
          :
            new TextView[] {initialVoltage, initialVoltage, finalVoltage, resistance, cycle, filterIndutance,
                filterCapacitance, voltageOscilation, currentOscilation, frequency};

        ArrayList<SpinnerCheckbox> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            SpinnerCheckbox stateVO = new SpinnerCheckbox();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            stateVO.setEditText(editTexts[i]);
            stateVO.setTextView(textViews[i]);
            listVOs.add(stateVO);
        }

        CustomAdapter myAdapter = new CustomAdapter(ConverterCCCC.this, 0, listVOs);
        spinnerData.setAdapter(myAdapter);
    }

    private void nextButtonConfig() {
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConverterCCCC.this, ResultCCCC.class);
            put(intent,getString(R.string.key_cycle), editCycle);
            put(intent,getString(R.string.key_initialVoltage), editInitialVoltage);
            put(intent, getString(R.string.key_finalVoltage), editFinalVoltage);

            put(intent,getString(R.string.key_resistance), editResistance);
            put(intent, getString(R.string.key_loadIndutance), editLoadIndutance);

            put(intent, getString(R.string.key_frequency), editFrequency);
            put(intent, getString(R.string.key_currentOscilation), editCurrentOscilation);
            put(intent, getString(R.string.key_voltageOscilation), editVoltageOscilation);

            put(intent, getString(R.string.key_filterIndutance), editFilterIndutance);
            put(intent, getString(R.string.key_capacitance), editFilterCapacitance);

            intent.putExtra(getString(R.string.key_load), getItem(spinnerLoad));
            startActivity(intent);
        });
    }


    private void backButtonConfig() {
        backButton.setOnClickListener(v -> finish());
    }

}