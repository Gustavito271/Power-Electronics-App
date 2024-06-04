package com.example.appdidatico_tcc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultCCCC extends AppCompatActivity {
    private double cycle;
    private double initialVoltage, avgVoltage;
    private double resistance, loadIndutance;
    private double filterCapacitance, filterIndutance;
    private double currentOscilation, voltageOscilation;
    private double frequency;

    private String load;
    ImageButton backButton;

    TextView ansResistance, ansLoadIndutance, ansFilterIndutance, ansCapacitance;
    TextView ansMinIndutance, ansMinCapacitance;
    TextView ansCycle, ansTransistorCurrent, ansDiodeCurrent;
    TextView ansAvgVoltage, ansAvgCurrent;
    TextView ansSourceVoltage, ansSourceCurrent;
    TextView ansSourcePower, ansAvgPower;
    TextView ansVoltageOscilation, ansCurrentOscilation;

    LinearLayout boxOscilation, boxFilter, boxMinimum;

    TextFormats formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_cccc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle params = getIntent().getExtras();

        if (params != null) {
            cycle = params.getDouble(getString(R.string.key_cycle));

            initialVoltage = params.getDouble(getString(R.string.key_initialVoltage));
            avgVoltage = params.getDouble(getString(R.string.key_finalVoltage));

            resistance = params.getDouble(getString(R.string.key_resistance));
            loadIndutance = params.getDouble(getString(R.string.key_loadIndutance))/1000;

            filterIndutance = params.getDouble(getString(R.string.key_filterIndutance))/1000;
            filterCapacitance = params.getDouble(getString(R.string.key_capacitance))/1000000;

            frequency = params.getDouble(getString(R.string.key_frequency));

            voltageOscilation = params.getDouble(getString(R.string.key_voltageOscilation));
            currentOscilation = params.getDouble(getString(R.string.key_currentOscilation));

            load = params.getString(getString(R.string.key_load));
        }

        formatter = new TextFormats(this);

        initializeComponents();
        backButtonConfig();


        if (load.equals(getString(R.string.CCCC_RLoad))) {
            equationsRLoad();
            resultsRLoad();
        } else if (load.equals(getString(R.string.RLLoad))) {
            equationsRLLoad();
            resultsRLLoad();
        } else if (load.equals(getString(R.string.CCCC_LCFilter))) {
            equationsLCFilter();
            resultsLCFilter();
        }
    }

    private void initializeComponents() {
        boxOscilation = findViewById(R.id.box_res_oscilacoes_CCCC);
        boxFilter = findViewById(R.id.box_res_filtro_CCCC);
        boxMinimum = findViewById(R.id.box_res_minimos_CCCC);
        backButton = findViewById(R.id.voltar_resCCCC);

        ansSourceVoltage = findViewById(R.id.resTensaoFonteCCCC);
        ansSourceCurrent = findViewById(R.id.resCorrenteFonteCCCC);

        ansAvgVoltage = findViewById(R.id.resTensaoMediaCCCC);
        ansAvgCurrent = findViewById(R.id.resCorrenteMediaCCCC);

        ansCycle = findViewById(R.id.resCicloCCCC);

        ansTransistorCurrent = findViewById(R.id.resCorrenteChaveamentoCCCC);
        ansDiodeCurrent = findViewById(R.id.resCorrenteDiodoCCCC);

        ansSourcePower = findViewById(R.id.resPotenciaEntradaCCCC);
        ansAvgPower = findViewById(R.id.resPotenciaSaidaCCCC);

        ansVoltageOscilation = findViewById(R.id.resOscilacaoTensaoCCCC);
        ansCurrentOscilation = findViewById(R.id.resOscilacaoCorrenteCCCC);

        ansResistance = findViewById(R.id.resResistenciaCCCC);
        ansLoadIndutance = findViewById(R.id.resIndutanciaCargaCCCC);
        ansCapacitance = findViewById(R.id.resCapacitanciaCCCC);
        ansFilterIndutance = findViewById(R.id.resIndutanciaFiltroCCCC);

        ansMinIndutance = findViewById(R.id.resIndutanciaMinCCCC);
        ansMinCapacitance = findViewById(R.id.resCapacitanciaMinCCCC);
    }

    private void backButtonConfig() {
        backButton.setOnClickListener(v -> finish());
    }

    private void defaultEquations() {
        ansSourceVoltage.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaTensaoEntrada), 1, 3, 7, 13));
        ansSourceCurrent.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCorrenteEntrada), 1, 2, 6, 12));

        ansAvgVoltage.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaTensaoMedia), 1, 7, 13, 15));
        ansAvgCurrent.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCorrenteMedia), 1, 7, 11, 17));

        ansCycle.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCiclo), 5, 7, 13, 14, 16, 18));

        ansTransistorCurrent.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCorrenteChaveamento), 1, 3, 7, 8));
        ansDiodeCurrent.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCorrenteDiodo), 1, 2, 6, 7));

        ansSourcePower.setTooltipText(formatter.formatString(getString(R.string.CCCC_potenciaEntrada), 1, 3, 7, 9, 11, 12));
        ansAvgPower.setTooltipText(formatter.formatString(getString(R.string.CCCC_potenciaSaida), 1, 2, 6, 12, 14, 20));
    }

    private void equationsRLoad() {
        defaultEquations();

        boxOscilation.setVisibility(View.GONE);
        boxFilter.setVisibility(View.GONE);
        boxMinimum.setVisibility(View.GONE);
        ansLoadIndutance.setVisibility(View.GONE);
    }

    private void resultsRLoad() {
        double avgCurrent, sourceCurrent;
        double transistorCurrent, diodeCurrent;
        double powerSource, powerAvg;

        cycle = cycle == 0 ? avgVoltage / initialVoltage : cycle;
        initialVoltage = initialVoltage == 0 ? avgVoltage / cycle : initialVoltage;
        avgVoltage = avgVoltage == 0 ? cycle * initialVoltage : avgVoltage;

        avgCurrent = avgVoltage / resistance;
        sourceCurrent = avgCurrent* cycle;

        transistorCurrent = avgCurrent* cycle;
        diodeCurrent = avgCurrent*(1- cycle);

        powerSource = initialVoltage * sourceCurrent;
        powerAvg = avgVoltage * avgCurrent;

        ansSourceVoltage.setText(formatter.formatString("Vin = " + formatter.notationValue(initialVoltage, "V"), 1, 3));
        ansSourceCurrent.setText(formatter.formatString("Is = " + formatter.notationValue(sourceCurrent, "A"), 1, 2));

        ansAvgVoltage.setText(formatter.formatString("Vo(avg) = " + formatter.notationValue(avgVoltage, "V"), 1, 7));
        ansAvgCurrent.setText(formatter.formatString("Io(avg) = " + formatter.notationValue(avgCurrent, "A"), 1, 7));

        ansCycle.setText("D = " + String.format("%.2f", cycle));
        ansTransistorCurrent.setText(formatter.formatString("Ich = " + formatter.notationValue(transistorCurrent, "A"), 1, 3));
        ansDiodeCurrent.setText(formatter.formatString("ID = " + formatter.notationValue(diodeCurrent, "A"), 1, 2));

        ansSourcePower.setText(formatter.formatString("Pin = " + formatter.notationValue(powerSource, "W"), 1, 3));
        ansAvgPower.setText(formatter.formatString("Po = " + formatter.notationValue(powerAvg, "W"), 1, 2));

        ansResistance.setText("R = " + formatter.notationValue(resistance, getString(R.string.ohm)));
    }

    private void equationsRLLoad() {
        defaultEquations();
        ansCurrentOscilation.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaOscilacaoCorrente), 2, 3, 8, 14));
        ansLoadIndutance.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaIndutancia), 6, 12, 25, 26));
        ansMinIndutance.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaIndutanciaMin), 1, 4));

        ansVoltageOscilation.setVisibility(View.GONE);
        ansMinCapacitance.setVisibility(View.GONE);
        boxFilter.setVisibility(View.GONE);
    }

    private void resultsRLLoad() {
        double avgCurrent, sourceCurrent;
        double transistorCurrent, diodeCurrent;
        double powerSource, powerAvg;
        double minIndutance;

        avgVoltage = avgVoltage == 0 ? cycle * initialVoltage : avgVoltage;
        cycle = cycle == 0 ? avgVoltage / initialVoltage : cycle;
        initialVoltage = initialVoltage == 0 ? avgVoltage / cycle : initialVoltage;

        avgCurrent = avgVoltage / resistance;
        sourceCurrent = avgCurrent* cycle;

        transistorCurrent = avgCurrent* cycle;
        diodeCurrent = avgCurrent*(1- cycle);

        powerSource = initialVoltage * sourceCurrent;
        powerAvg = avgVoltage * avgCurrent;

        minIndutance = (1- cycle)* resistance /(2* frequency);

        currentOscilation = currentOscilation == 0
          ?
            loadIndutance == 0
            ?
              avgVoltage *(1- cycle)/(frequency *minIndutance)
            :
              avgVoltage *(1- cycle)/(frequency * loadIndutance)
          :
            avgCurrent* currentOscilation /100;

        loadIndutance = loadIndutance == 0
          ?
            avgVoltage *(1- cycle)/(frequency * currentOscilation)
          :
            loadIndutance;


        ansSourceVoltage.setText(formatter.formatString("Vin = " + formatter.notationValue(initialVoltage, "V"), 1, 3));
        ansSourceCurrent.setText(formatter.formatString("Is = " + formatter.notationValue(sourceCurrent, "A"), 1, 2));

        ansAvgVoltage.setText(formatter.formatString("Vo(avg) = " + formatter.notationValue(avgVoltage, "V"), 1, 7));
        ansAvgCurrent.setText(formatter.formatString("Io(avg) = " + formatter.notationValue(avgCurrent, "A"), 1, 7));

        ansCycle.setText("D = " + String.format("%.2f", cycle));
        ansTransistorCurrent.setText(formatter.formatString("Ich = " + formatter.notationValue(transistorCurrent,"A"), 1, 3));
        ansDiodeCurrent.setText(formatter.formatString("ID = " + formatter.notationValue(diodeCurrent, "A"), 1, 2));

        ansSourcePower.setText(formatter.formatString("Pin = " + formatter.notationValue(powerSource, "W"), 1, 3));
        ansAvgPower.setText(formatter.formatString("Po = " + formatter.notationValue(powerAvg, "W"), 1, 2));

        ansCurrentOscilation.setText(formatter.formatString(getString(R.string.delta) + "Io = " + formatter.notationValue(currentOscilation, "A"), 2, 3));
        ansLoadIndutance.setText("L = " + formatter.notationValue(loadIndutance, "H"));
        ansResistance.setText("R = " + formatter.notationValue(resistance, getString(R.string.ohm)));

        ansMinIndutance.setText(formatter.formatString("Lmin = " + formatter.notationValue(minIndutance, "H"), 1, 4));
    }

    private void equationsLCFilter() {
        defaultEquations();
        ansCurrentOscilation.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaOscilacaoCorrente), 2, 3, 8, 14));
        ansVoltageOscilation.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaOscilacaoTensao), 2, 3, 8, 14));

        ansFilterIndutance.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaIndutancia), 6, 12, 25, 26));
        ansMinIndutance.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaIndutanciaMin), 1, 4));

        ansCapacitance.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCapacitancia), 6, 12, 27, 28));
        ansMinCapacitance.setTooltipText(formatter.formatString(getString(R.string.CCCC_formulaCapacitanciaMin), 1, 4));

        ansLoadIndutance.setVisibility(View.GONE);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void resultsLCFilter() {
        double avgCurrent, sourceCurrent;
        double transistorCurrent, diodeCurrent;
        double powerSource, powerAvg;
        double minIndutance, minCapacitance;

        avgVoltage = avgVoltage == 0 ? cycle * initialVoltage : avgVoltage;
        cycle = cycle == 0 ? avgVoltage / initialVoltage : cycle;
        initialVoltage = initialVoltage == 0 ? avgVoltage / cycle : initialVoltage;

        avgCurrent = avgVoltage / resistance;
        sourceCurrent = avgCurrent* cycle;

        transistorCurrent = avgCurrent* cycle;
        diodeCurrent = avgCurrent*(1- cycle);

        powerSource = initialVoltage * sourceCurrent;
        powerAvg = avgVoltage * avgCurrent;

        minIndutance = (1- cycle)* resistance /(2* frequency);
        minCapacitance = filterIndutance == 0
          ?
            (1- cycle)/(16*minIndutance*Math.pow(frequency,2))
          :
            (1- cycle)/(16* filterIndutance *Math.pow(frequency,2));

        currentOscilation = currentOscilation == 0
          ?
            filterIndutance == 0
            ?
              avgVoltage *(1- cycle)/(frequency *minIndutance)
            :
              avgVoltage *(1- cycle)/(frequency * filterIndutance)
          :
            avgCurrent* currentOscilation /100;

        filterIndutance = filterIndutance == 0
          ?
            avgVoltage *(1- cycle)/(frequency * currentOscilation)
          :
            filterIndutance;

        voltageOscilation = voltageOscilation == 0
          ?
            filterCapacitance == 0
            ?
              avgVoltage *(1- cycle)/(8* filterIndutance *minCapacitance*Math.pow(frequency,2))
            :
              avgVoltage *(1- cycle)/(8* filterIndutance * filterCapacitance *Math.pow(frequency,2))
          :
            avgVoltage * voltageOscilation /100;

        filterCapacitance = filterCapacitance == 0
          ?
            avgVoltage *(1- cycle)/(8* filterIndutance * voltageOscilation *Math.pow(frequency,2))
          :
            filterCapacitance;

        ansSourceVoltage.setText(formatter.formatString("Vin = " + formatter.notationValue(initialVoltage, "V"), 1, 3));
        ansSourceCurrent.setText(formatter.formatString("Is = " + formatter.notationValue(sourceCurrent, "A"), 1, 2));

        ansAvgVoltage.setText(formatter.formatString("Vo(avg) = " + formatter.notationValue(avgVoltage, "V"), 1, 7));
        ansAvgCurrent.setText(formatter.formatString("Io(avg) = " + formatter.notationValue(avgCurrent, "A"), 1, 7));

        ansCycle.setText("D = " + String.format("%.2f", cycle));
        ansTransistorCurrent.setText(formatter.formatString("Ich = " + formatter.notationValue(transistorCurrent, "A"), 1, 3));
        ansDiodeCurrent.setText(formatter.formatString("ID = " + formatter.notationValue(diodeCurrent, "A"), 1, 2));

        ansSourcePower.setText(formatter.formatString("Pin = " + formatter.notationValue(powerSource, "W"), 1, 3));
        ansAvgPower.setText(formatter.formatString("Po = " + formatter.notationValue(powerAvg, "W"), 1, 2));

        ansVoltageOscilation.setText(formatter.formatString(getString(R.string.delta) + "Vo = " + formatter.notationValue(voltageOscilation, "V"), 2, 3));
        ansCurrentOscilation.setText(formatter.formatString(getString(R.string.delta) + "Io = " + formatter.notationValue(currentOscilation, "A"), 2, 3));


        ansFilterIndutance.setText("L = " + formatter.notationValue(filterIndutance, "H"));
        ansCapacitance.setText("C = " + formatter.notationValue(filterCapacitance, "F"));

        ansResistance.setText("R = " + formatter.notationValue(resistance, getString(R.string.ohm)));

        ansMinIndutance.setText(formatter.formatString("Lmin = " + formatter.notationValue(minIndutance, "H"), 1, 4));
        ansMinCapacitance.setText(formatter.formatString("Cmin = " + formatter.notationValue(minCapacitance,"F"), 1, 4));
    }
}