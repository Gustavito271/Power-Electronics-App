package com.example.appdidatico_tcc;

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

public class ResultCACC extends AppCompatActivity {

    private double peakVoltage, resistance, capacitance, voltage, oscilation, frequency;
    private String type, wave, load;
    
    TextView ansVoCC, ansIoCC;
    TextView ansVin, ansVinPeak;
    TextView ansVoRMS, ansIoRMS;
    TextView ansVoCA, ansIoCA;
    TextView ansPowerCC, ansPowerCA, ansPerformance;
    TextView ansVoltageFF, ansVoltageRF, ansCurrentFF, ansCurrentRF;
    TextView ansCurrentFAVDiode, ansVoltagediode, ansCurrentDiode;
    TextView ansVoltageOscilation, ansCapacitance;

    LinearLayout mediumValue, effectiveValue, CAValue, performance, factorsV, diode, factorsI, oscilationCapacitance;
    ImageButton backButton;
    TextFormats formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_cacc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle params = getIntent().getExtras();
        if (params != null) {
            voltage = params.getDouble(getString(R.string.key_initialVoltage));
            peakVoltage = params.getDouble(getString(R.string.key_voltageP));
            if (voltage == 0) {
                voltage = peakVoltage /Math.sqrt(2);
            } else {
                peakVoltage = voltage *Math.sqrt(2);
            }
            resistance = params.getDouble(getString(R.string.key_resistance));
            type = params.getString(getString(R.string.key_type));
            wave = params.getString(getString(R.string.key_wave));
            load = params.getString(getString(R.string.key_load));
            capacitance = params.getDouble(getString(R.string.key_capacitance))/1000000;
            oscilation = params.getDouble(getString(R.string.key_voltageOscilation));
            frequency = params.getDouble(getString(R.string.key_frequency));
        }

        formatter = new TextFormats(this);

        initializeComponents();
        backButtonConfig();

        if (type.equals(getString(R.string.Monophase))) {
            if (wave.equals(getString(R.string.CACC_halfWave))) {
                monoHalfEquations();
                monoHalfResults();
            } else if (wave.equals(getString(R.string.CACC_fullWave))){
                monoFullEquations();
                monoFullResults();
            }
        } else if (type.equals(getString(R.string.Threephase))) {
            threeEquations();
            threeResults();
        }
    }

    private void initializeComponents() {
        ansVin = findViewById(R.id.resTensaoL);
        ansVinPeak = findViewById(R.id.resTensaoP);
        ansVoCC = findViewById(R.id.resRetMedia);
        ansIoCC = findViewById(R.id.resCorrenteMedia);
        ansVoRMS = findViewById(R.id.resRetEficaz);
        ansIoRMS = findViewById(R.id.resCorrenteEficaz);
        ansVoCA = findViewById(R.id.resTensaoAC);
        ansIoCA = findViewById(R.id.resCorrenteAC);
        ansPowerCC = findViewById(R.id.resPotenciaDC);
        ansPowerCA = findViewById(R.id.resPotenciaAC);
        ansPerformance = findViewById(R.id.resRendimento);
        ansVoltageFF = findViewById(R.id.resFF);
        ansVoltageRF = findViewById(R.id.resRF);
        ansCurrentFAVDiode = findViewById(R.id.resDiodoCorrente);
        ansVoltagediode = findViewById(R.id.resDiodoTensao);
        ansCurrentDiode = findViewById(R.id.resDiodoCorrente2);
        ansCurrentFF = findViewById(R.id.resFFCorrente);
        ansCurrentRF = findViewById(R.id.resRFCorrente);
        ansVoltageOscilation = findViewById(R.id.resDelta);
        ansCapacitance = findViewById(R.id.resCap);

        mediumValue = findViewById(R.id.box_res_valor_medio);
        effectiveValue = findViewById(R.id.box_res_valor_eficaz);
        CAValue = findViewById(R.id.box_res_valor_AC);
        performance = findViewById(R.id.box_res_potencias);
        factorsV = findViewById(R.id.box_res_fator_forma);
        diode = findViewById(R.id.box_res_diodo_config);
        factorsI = findViewById(R.id.box_res_fator_forma_corrente);
        oscilationCapacitance = findViewById(R.id.box_res_capDelta);

        backButton = findViewById(R.id.voltar_resCACC);
    }

    private void backButtonConfig() {
        backButton.setOnClickListener(v -> finish());
    }

    private void defaultEquations() {
        ansVin.setTooltipText(formatter.formatString(getString(R.string.formulaTensaoFonte), 11,12, 16, 17));
        ansVinPeak.setTooltipText(formatter.formatString(getString(R.string.formulaTensaoPicoS), 11,12, 16, 17));

        ansPowerCC.setTooltipText(formatter.formatString(getString(R.string.formulaPotenciaMedia), 11,16, 20, 25, 27, 32));
        ansPowerCA.setTooltipText(formatter.formatString(getString(R.string.formulaPotenciaEficaz), 11,17, 21, 27, 29, 35));
        ansPerformance.setTooltipText(formatter.formatString(getString(R.string.formulaRendimento), 15,20, 22, 28));

        ansVoltageFF.setTooltipText(formatter.formatString(getString(R.string.formulaFatorFormaTensao), 16,22, 24, 29));
        ansVoltageRF.setTooltipText(formatter.formatString(getString(R.string.formulaFatorRippleTensao), 16,21, 23, 28));

        ansCurrentFF.setTooltipText(formatter.formatString(getString(R.string.formulaFatorFormaCorrente), 16,22, 24, 29));
        ansCurrentRF.setTooltipText(formatter.formatString(getString(R.string.formulaFatorRippleCorrente), 16,21, 23, 28));

    }

    private void monoHalfEquations() {
        diode.setVisibility(View.GONE);
        oscilationCapacitance.setVisibility(View.GONE);

        defaultEquations();

        ansVoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoM_formulaTensaoMedia), 11,16, 20, 21));
        ansIoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoM_formulaCorrenteMedia), 11,16, 20, 25));

        ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoM_formulaTensaoEficaz), 11,17, 21, 22));
        ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoM_formulaCorrenteEficaz), 11,17, 21, 27));

        ansVoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoM_formulaTensaoCA), 11,16, 22, 28, 34, 39));
        ansIoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoM_formulaCorrenteCA), 11,16, 20, 25));
    }

    private void monoHalfResults() {
        double avgVoltage, avgCurrent;
        double effectiveVoltage, effectiveCurrent;
        double voltageCA, currentCA;
        double powerDC, powerEffective;
        double performance;
        double voltageFF, voltageRF;
        double currentFF, currentFR;

        avgVoltage = peakVoltage /Math.PI;
        avgCurrent = avgVoltage/ resistance;

        effectiveVoltage = peakVoltage /2;
        effectiveCurrent = effectiveVoltage/ resistance;

        voltageCA = Math.sqrt(Math.pow(effectiveVoltage, 2) - Math.pow(avgVoltage, 2));
        currentCA = voltageCA/ resistance;

        powerDC = avgVoltage*avgCurrent;
        powerEffective = effectiveVoltage*effectiveCurrent;

        performance = (powerDC/powerEffective)*100;

        voltageFF = effectiveVoltage/avgVoltage;
        voltageRF = voltageCA/avgVoltage;

        currentFF = effectiveCurrent/avgCurrent;
        currentFR = currentCA/avgCurrent;

        ansVin.setText(formatter.formatString("VS = " + formatter.notationValue(voltage, "V"), 1, 2));
        ansVinPeak.setText(formatter.formatString("Vp = " + formatter.notationValue(peakVoltage, "V"), 1, 2));

        ansVoCC.setText(formatter.formatString("Vo(DC) = " + formatter.notationValue(avgVoltage, "V"), 1, 6));
        ansIoCC.setText(formatter.formatString("Io(DC) = " + formatter.notationValue(avgCurrent,"A"), 1, 6));

        ansVoRMS.setText(formatter.formatString("Vo(rms) = " + formatter.notationValue(effectiveVoltage,"V"), 1, 7) );
        ansIoRMS.setText(formatter.formatString("Io(rms) = " + formatter.notationValue(effectiveCurrent, "A"), 1, 7));

        ansVoCA.setText(formatter.formatString("Vo(CA) = " + formatter.notationValue(voltageCA, "V"), 1, 6) );
        ansIoCA.setText(formatter.formatString("Io(CA) = " + formatter.notationValue(currentCA, "A"), 1, 6));

        ansPowerCC.setText(formatter.formatString("Po(DC) = " + formatter.notationValue(powerDC, "W"), 1, 6));
        ansPowerCA.setText(formatter.formatString("Po(rms) = " + formatter.notationValue(powerEffective, "W"), 1, 7));
        ansPerformance.setText(getString(R.string.eta) + " = " + formatter.notationValue(performance, "%"));

        ansVoltageFF.setText("FF = " + String.format("%.2f", voltageFF));
        ansVoltageRF.setText("FR = " + String.format("%.2f", voltageRF));

        ansCurrentFF.setText("FF = " + String.format("%.2f", currentFF));
        ansCurrentRF.setText("FR = " + String.format("%.2f", currentFR));

    }

    private void monoFullEquations() {
        defaultEquations();

        if (load.equals(getString(R.string.RLoad))) {
            ansVoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoMedia), 11,16, 23, 24));
            ansIoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteMedia), 11,16, 20, 25));

            ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoEficaz), 11,17, 21, 22));
            ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteEficaz), 11,17, 21, 27));

            ansVoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoCA), 11,16, 22, 28, 34, 39));
            ansIoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteCA), 11,16, 20, 25));

        } else if (load.equals(getString(R.string.RLLoad))){
            ansVoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoMedia), 11,16, 23, 24));
            ansIoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteMedia), 11,16, 20, 25));

            ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoEficaz), 11,17, 21, 22));
            ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteEficaz2), 11,17, 21, 26));

            ansVoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoCA), 11,16, 22, 28, 34, 39));
            ansIoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteCA2), 11,16));

        } else {
            oscilationCapacitance.setVisibility(View.VISIBLE);

            ansVoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoMedia2), 11,16, 20, 21));
            ansIoCC.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteMedia), 11,16, 20, 25));

            ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoEficaz2), 11,17, 21, 22));
            ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteEficaz2), 11,17, 21, 26));

            ansVoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaTensaoCA2), 11,16));
            ansIoCA.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteCA2), 11,16));
        }

        ansCurrentFAVDiode.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteDiodoFAV), 11,17, 21, 26));
        ansCurrentDiode.setTooltipText(formatter.formatString(getString(R.string.monofasicoC_formulaCorrenteDiodoRMS), 11,16, 20, 26));
        ansVoltagediode.setTooltipText(formatter.formatString(getString(R.string.formulaTensaoDiodo), 11,14, 22, 23));

        if (oscilation == 0) {
            ansVoltageOscilation.setTooltipText(formatter.formatString(getString(R.string.formulaDeltaVo2), 12,13, 17, 18));
        } else {
            ansVoltageOscilation.setTooltipText(formatter.formatString(getString(R.string.formulaDeltaVo1), 12,16, 21, 25, 27, 28));
        }
        ansCapacitance.setTooltipText(formatter.formatString(getString(R.string.formulaCapacitancia), 15,16, 23, 24));
    }

    private void monoFullResults() {
        double avgVoltage, avgCurrent;
        double effectiveVoltage, effectiveCurrent;
        double voltageCA, currentCA;
        double powerCC, powerEffective;
        double performance;
        double diodeCurrentFAV, diodeVoltage, diodeCurrentEffective;
        double voltageFF, voltageRF;
        double currentFF, currentRF;

        if (load.equals(getString(R.string.RLoad))) {
            avgVoltage = (2* peakVoltage)/Math.PI;
            avgCurrent = avgVoltage/ resistance;

            effectiveVoltage = peakVoltage /Math.sqrt(2);
            effectiveCurrent = effectiveVoltage/ resistance;

            voltageCA = Math.sqrt(Math.pow(effectiveVoltage, 2) - Math.pow(avgVoltage, 2));
            currentCA = voltageCA/ resistance;

            oscilationCapacitance.setVisibility(View.GONE);
        } else if (load.equals(getString(R.string.RLLoad))){
            avgVoltage = (2* peakVoltage)/Math.PI;
            avgCurrent = avgVoltage/ resistance;

            effectiveVoltage = peakVoltage /Math.sqrt(2);
            effectiveCurrent = avgCurrent;

            voltageCA = Math.sqrt(Math.pow(effectiveVoltage, 2) - Math.pow(avgVoltage, 2));
            currentCA = 0;

            oscilationCapacitance.setVisibility(View.GONE);
        } else {
            avgVoltage = peakVoltage;
            avgCurrent = peakVoltage / resistance;

            effectiveVoltage = peakVoltage;
            effectiveCurrent = avgCurrent;

            voltageCA = 0;
            currentCA = 0;

            if (oscilation != 0) {
                oscilation = avgVoltage* oscilation /100;
                capacitance =  peakVoltage /(2* frequency * resistance * oscilation);
            } else {
                oscilation =  peakVoltage /(2* frequency * resistance * capacitance);
            }
        }

        powerCC = avgVoltage*avgCurrent;
        powerEffective = effectiveVoltage*effectiveCurrent;
        performance = (powerCC/powerEffective)*100;

        diodeCurrentFAV = avgCurrent/2;
        diodeCurrentEffective = effectiveCurrent/Math.sqrt(2);
        diodeVoltage = 1.2* peakVoltage;

        voltageFF = effectiveVoltage/avgVoltage;
        voltageRF = voltageCA/avgVoltage;

        currentFF = effectiveCurrent/avgCurrent;
        currentRF = currentCA/avgCurrent;

        ansVin.setText(formatter.formatString("VS = " + formatter.notationValue(voltage, "V"), 1, 2));
        ansVinPeak.setText(formatter.formatString("Vp = " + formatter.notationValue(peakVoltage, "V"), 1, 2));

        ansVoCC.setText(formatter.formatString("Vo(DC) = " + formatter.notationValue(avgVoltage, "V"), 1, 6));
        ansIoCC.setText(formatter.formatString("Io(DC) = " + formatter.notationValue(avgCurrent, "A"), 1, 6));

        ansVoRMS.setText(formatter.formatString("Vo(rms) = " + formatter.notationValue(effectiveVoltage, "V"), 1, 7) );
        ansIoRMS.setText(formatter.formatString("Io(rms) = " + formatter.notationValue(effectiveCurrent, "A"), 1, 7));

        ansVoCA.setText(formatter.formatString("Vo(CA) = " + formatter.notationValue(voltageCA, "V"), 1, 6) );
        ansIoCA.setText(formatter.formatString("Io(CA) = " + formatter.notationValue(currentCA, "A"), 1, 6));

        ansPowerCC.setText(formatter.formatString("Po(DC) = " + formatter.notationValue(powerCC, "W"), 1, 6));
        ansPowerCA.setText(formatter.formatString("Po(rms) = " + formatter.notationValue(powerEffective, "W"), 1, 7));
        ansPerformance.setText(getString(R.string.eta) + " = " + formatter.notationValue(performance, "%"));

        ansCurrentFAVDiode.setText(formatter.formatString("IF(AV) = " + formatter.notationValue(diodeCurrentFAV, "A"), 1, 6));
        ansCurrentDiode.setText(formatter.formatString("IF(rms) = " + formatter.notationValue(diodeCurrentEffective, "A"), 1, 7));
        ansVoltagediode.setText(formatter.formatString("VRRM = " + formatter.notationValue(diodeVoltage, "V"), 1, 4));

        ansVoltageFF.setText("FF = " + String.format("%.2f", voltageFF));
        ansVoltageRF.setText("FR = " + String.format("%.2f", voltageRF));

        ansCurrentFF.setText("FF = " + String.format("%.2f", currentFF));
        ansCurrentRF.setText("FR = " + String.format("%.2f", currentRF));

        ansVoltageOscilation.setText(formatter.formatString(getString(R.string.delta) + "Vo = " + formatter.notationValue(oscilation, "V"), 2, 3));
        ansCapacitance.setText("C = " + formatter.notationValue(capacitance, "F"));
    }

    private void threeEquations() {
        defaultEquations();

        if (load.equals(getString(R.string.RLoad))) {
            ansVoCC.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoMedia), 11,16, 25, 26));
            ansIoCC.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteMedia), 11,16, 20, 25));

            ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoEficaz), 11,17, 28, 29));
            ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteEficaz), 11,17, 21, 27));

            ansVoCA.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoCA), 11,16, 22, 28, 34, 39));
            ansIoCA.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteCA), 11,16, 20, 25));

        } else if (load.equals(getString(R.string.RLLoad))){
            ansVoCC.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoMedia), 11,16, 25, 26));
            ansIoCC.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteMedia), 11,16, 20, 25));

            ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoEficaz), 11,17, 28, 29));
            ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteEficaz2), 11,17, 21, 26));

            ansVoCA.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoCA), 11,16, 22, 28, 34, 39));
            ansIoCA.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteCA2), 11,16));

        } else {
            oscilationCapacitance.setVisibility(View.VISIBLE);

            ansVoCC.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoMedia2), 11,16, 20, 21));
            ansIoCC.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteMedia), 11,16, 20, 25));

            ansVoRMS.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoEficaz2), 11,17, 21, 26));
            ansIoRMS.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteEficaz2), 11,17, 21, 26));

            ansVoCA.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaTensaoCA2), 11,16));
            ansIoCA.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteCA2), 11,16));
        }

        ansCurrentFAVDiode.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteDiodoFAV), 11,17, 21, 26));
        ansCurrentDiode.setTooltipText(formatter.formatString(getString(R.string.trifasico_formulaCorrenteDiodoRMS), 11,16, 20, 26));
        ansVoltagediode.setTooltipText(formatter.formatString(getString(R.string.formulaTensaoDiodo), 11,14, 22, 23));

        if (oscilation == 0) {
            ansVoltageOscilation.setTooltipText(formatter.formatString(getString(R.string.formulaDeltaVo2), 12,13, 17, 18));
        } else {
            ansVoltageOscilation.setTooltipText(formatter.formatString(getString(R.string.formulaDeltaVo1), 12,16, 21, 25, 27, 28));
        }
        ansCapacitance.setTooltipText(formatter.formatString(getString(R.string.formulaCapacitancia), 15,16, 23, 24));
    }

    private void threeResults() {
        double avgVoltage, avgCurrent;
        double effectiveVoltage, effectiveCurrent;
        double voltageCA, currentCA;
        double powerCC, powerEffective;
        double performance;
        double diodeCurrentFAV, currentDiodeRMS, diodeVoltage;
        double voltageFF, voltageFR;
        double currentFF, currentFR;
        boolean isR = load.equals(getString(R.string.RLoad));
        boolean isRC = load.equals(getString(R.string.CACC_RCLoad));

        if (isR) {
            avgVoltage = 1.35* voltage;
            avgCurrent = avgVoltage/ resistance;

            effectiveVoltage = 1.3516* voltage;
            effectiveCurrent = effectiveVoltage/ resistance;

            voltageCA = Math.sqrt(Math.pow(effectiveVoltage, 2) - Math.pow(avgVoltage, 2));
            currentCA = voltageCA/ resistance;

        } else if (isRC){
            avgVoltage = peakVoltage;
            avgCurrent = avgVoltage/ resistance;

            effectiveVoltage = avgVoltage;
            effectiveCurrent = avgCurrent;

            voltageCA = 0;
            currentCA = 0;

            if (oscilation != 0) {
                oscilation = avgVoltage* oscilation /100;
                capacitance =  peakVoltage /(2* frequency * resistance * oscilation);
            } else {
                oscilation =  peakVoltage /(2* frequency * resistance * capacitance);
            }
        } else {
            avgVoltage = 1.35* voltage;
            avgCurrent = avgVoltage/ resistance;

            effectiveVoltage = 1.3516* voltage;
            effectiveCurrent = avgCurrent;

            voltageCA = Math.sqrt(Math.pow(effectiveVoltage, 2) - Math.pow(avgVoltage, 2));
            currentCA = 0;
        }

        powerCC = avgVoltage*avgCurrent;
        powerEffective = effectiveVoltage*effectiveCurrent;
        performance = (powerCC/powerEffective)*100;

        diodeCurrentFAV = avgCurrent/3;
        currentDiodeRMS = avgCurrent/Math.sqrt(3);
        diodeVoltage = 1.2* peakVoltage;

        voltageFF = effectiveVoltage/avgVoltage;
        voltageFR = voltageCA/avgVoltage;

        currentFF = effectiveCurrent/avgCurrent;
        currentFR = currentCA/avgCurrent;

        ansVin.setText(formatter.formatString("VL = " + formatter.notationValue(voltage, "V"), 1, 2));
        ansVinPeak.setText(formatter.formatString("Vp = " + formatter.notationValue(peakVoltage, "V"), 1, 2));

        ansVoCC.setText(formatter.formatString("Vo(DC) = " + formatter.notationValue(avgVoltage, "V"), 1, 6));
        ansIoCC.setText(formatter.formatString("Io(DC) = " + formatter.notationValue(avgCurrent, "A"), 1, 6));

        ansVoRMS.setText(formatter.formatString("Vo(rms) = " + formatter.notationValue(effectiveVoltage, "V"), 1, 7) );
        ansIoRMS.setText(formatter.formatString("Io(rms) = " + formatter.notationValue(effectiveCurrent, "A"), 1, 7));

        ansVoCA.setText(formatter.formatString("Vo(CA) = " + formatter.notationValue(voltageCA, "V"), 1, 6) );
        ansIoCA.setText(formatter.formatString("Io(CA) = " + formatter.notationValue(currentCA, "A"), 1, 6));

        ansPowerCC.setText(formatter.formatString("Po(DC) = " + formatter.notationValue(powerCC, "W"), 1, 6));
        ansPowerCA.setText(formatter.formatString("Po(rms) = " + formatter.notationValue(powerEffective, "W"), 1, 7));
        ansPerformance.setText(getString(R.string.eta) + " = " + formatter.notationValue(performance, "%"));

        ansCurrentFAVDiode.setText(formatter.formatString("IF(AV) = " + formatter.notationValue(diodeCurrentFAV, "A"), 1, 6));
        ansCurrentDiode.setText(formatter.formatString("IF(rms) = " + formatter.notationValue(currentDiodeRMS, "A"), 1, 7));
        ansVoltagediode.setText(formatter.formatString("VRRM = " + formatter.notationValue(diodeVoltage, "V"), 1, 4));

        ansVoltageFF.setText("FF = " + (!Double.isNaN(voltageFF) ? String.format("%.2f", voltageFF) : ""));
        ansVoltageRF.setText("FR = " + (!Double.isNaN(voltageFF) ? String.format("%.2f", voltageFR) : ""));

        ansCurrentFF.setText("FF = " + (!Double.isNaN(voltageFF) ? String.format("%.2f", currentFF) : ""));
        ansCurrentRF.setText("FR = " + (!Double.isNaN(voltageFF) ? String.format("%.2f", currentFR) : ""));

        ansVoltageOscilation.setText(formatter.formatString(getString(R.string.delta) + "Vo = " + formatter.notationValue(oscilation, "V"), 2, 3));
        ansCapacitance.setText("C = " + formatter.notationValue(capacitance,"F"));
    }
}