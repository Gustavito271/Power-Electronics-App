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

public class ResultCCCA extends AppCompatActivity {

    double ampModulation, voltage, resistance, inductance, frequency;
    String type, format;
    TextView ansVoltageRMS, ansCurrentRMS, ansVoltageL, ansCurrentL, ansVoltageF, ansCurrentF;
    TextView ansResistance, ansInductiveReact;
    TextView apparentPower, activePower, reactivePower;
    LinearLayout boxRMS, boxLine, boxPhase;
    ImageButton backButton;
    TextFormats formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_ccca);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        formatter = new TextFormats(this);

        Bundle params = getIntent().getExtras();
        if (params != null) {
            ampModulation = params.getDouble(getString(R.string.key_amplitudeModulation));
            voltage = params.getDouble(getString(R.string.key_initialVoltage));
            resistance = params.getDouble(getString(R.string.key_resistance));
            inductance = params.getDouble(getString(R.string.key_loadIndutance))/1000;
            frequency = params.getDouble(getString(R.string.key_frequency));
            type = params.getString(getString(R.string.key_type));
            format = params.getString(getString(R.string.key_format));
        }

        initializeComponents();
        backButtonConfig();

        if (type.equals(getString(R.string.Monophase))) {
            monoEquations();
            monoResults();
        } else if (type.equals(getString(R.string.Threephase))) {
            threeEquations();
            threeResults();
        }
    }

    private void initializeComponents() {
        ansResistance = findViewById(R.id.resResistencia_CCCA);
        ansInductiveReact = findViewById(R.id.resReatanciaIndutiva_CCCA);
        ansVoltageRMS = findViewById(R.id.resTensaoRMS_CCCA);
        ansCurrentRMS = findViewById(R.id.resCorrenteRMS_CCCA);
        ansVoltageL = findViewById(R.id.resTensaoL_CCCA);
        ansCurrentL = findViewById(R.id.resCorrenteL_CCCA);
        ansVoltageF = findViewById(R.id.resTensaoF_CCCA);
        ansCurrentF = findViewById(R.id.resCorrenteF_CCCA);

        apparentPower = findViewById(R.id.resPotenciaAparente_CCCA);
        activePower = findViewById(R.id.resPotenciaAtiva_CCCA);
        reactivePower = findViewById(R.id.resPotenciaReativa_CCCA);

        boxRMS = findViewById(R.id.valorRMS_resCCCA);
        boxLine = findViewById(R.id.valorL_resCCCA);
        boxPhase = findViewById(R.id.valorF_resCCCA);

        backButton = findViewById(R.id.voltar_resCCCA);
    }

    private void backButtonConfig() {
        backButton.setOnClickListener(v -> finish());
    }

    private void defaultEquations() {
        ansInductiveReact.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationInductance), 1,2));

        activePower.setTooltipText(getString(R.string.CCCA_equationP));
        reactivePower.setTooltipText(getString(R.string.CCCA_equationQ));
    }

    private void monoEquations() {
        defaultEquations();
        ansVoltageRMS.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationVoltage), 1,7, 11, 12, 15,20));
        ansCurrentRMS.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationCurrent), 1,7, 11, 17, 25, 26));

        apparentPower.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationSMono), 5,11, 13, 19));

        boxLine.setVisibility(View.GONE);
        boxPhase.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void monoResults() {
        double voltageRMS;
        double inductiveReact;
        double powerActive, powerReactive;
        ComplexValue reactance, currentRMS, powerApparent;

        voltageRMS = ampModulation*voltage/Math.sqrt(2);

        inductiveReact = 2*Math.PI*frequency*inductance;

        reactance = new ComplexValue(resistance, inductiveReact);
        reactance.transformToPolar();

        currentRMS = new ComplexValue(voltageRMS/reactance.getRealPart(), 0 - reactance.getImaginaryPart());

        powerApparent = new ComplexValue(voltageRMS * currentRMS.getRealPart(), Math.abs(currentRMS.getImaginaryPart()));
        powerApparent.transformToCartesian();

        powerActive = powerApparent.getRealPart();
        powerReactive = powerApparent.getImaginaryPart();

        currentRMS.transformToCartesian();

        ansVoltageRMS.setText(formatter.formatString("Vo(rms) = " + formatter.notationValue(voltageRMS, "V"), 1, 7));
        ansCurrentRMS.setText(formatter.formatString("Io(rms) = " + formatter.formatComplexValues(currentRMS)+ " A", 1, 7));

        ansResistance.setText("R = " + formatter.notationValue(resistance, getString(R.string.ohm)));
        ansInductiveReact.setText(formatter.formatString("XL = " + formatter.notationValue(inductiveReact, getString(R.string.ohm)), 1, 2));

        apparentPower.setText("S = " + formatter.formatComplexValues(powerApparent) + " VA");
        activePower.setText("P = " + formatter.notationValue(powerActive, "W"));
        reactivePower.setText("Q = " + formatter.notationValue(powerReactive, "Var"));

        if (inductance == 0) {
            ansInductiveReact.setVisibility(View.GONE);
        }
    }

    private void threeEquations() {
        defaultEquations();
        boolean isDelta = format.equals(getString(R.string.CCCA_delta));
        ansVoltageL.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationLineVoltage), 1,2, 11, 12, 14,19));
        ansCurrentL.setTooltipText(formatter.formatString(isDelta ? getString(R.string.CCCA_equationLineCurrentDelta) : getString(R.string.CCCA_equationLineCurrentStar), 1,2, 6, 7));

        ansVoltageF.setTooltipText(formatter.formatString(isDelta ? getString(R.string.CCCA_equationPhaseVoltageDelta) : getString(R.string.CCCA_equationPhaseVoltageStar), 1,2, 6, 7));
        ansCurrentF.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationPhaseCurrent), 1,2, 6, 7, 15, 16));

        apparentPower.setTooltipText(formatter.formatString(getString(R.string.CCCA_equationSThree), 8,9, 11, 12));
        boxRMS.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void threeResults() {
        boolean isDelta = format.equals(getString(R.string.CCCA_delta));
        double voltageL, voltageF;
        double inductiveReact;
        double powerActive, powerReactive;
        ComplexValue reactance, currentL, currentF, powerApparent;

        voltageL = 0.612*ampModulation*voltage;
        voltageF = !isDelta ? voltageL/Math.sqrt(3) : voltageL;

        inductiveReact = 2*Math.PI*frequency*inductance;

        reactance = new ComplexValue(resistance, inductiveReact);
        reactance.transformToPolar();

        currentF = new ComplexValue(voltageF/reactance.getRealPart(), 0 - reactance.getImaginaryPart());

        currentL = new ComplexValue(!isDelta ? currentF.getRealPart() : Math.sqrt(3)*currentF.getRealPart(), currentF.getImaginaryPart());

        powerApparent = new ComplexValue(voltageL * currentL.getRealPart() * Math.sqrt(3), Math.abs(currentL.getImaginaryPart()));
        powerApparent.transformToCartesian();

        powerActive = powerApparent.getRealPart();
        powerReactive = powerApparent.getImaginaryPart();

        currentF.transformToCartesian();
        currentL.transformToCartesian();

        ansVoltageL.setText(formatter.formatString("VL = " + formatter.notationValue(voltageL, "V"), 1, 2));
        ansCurrentL.setText(formatter.formatString("IL = " + formatter.formatComplexValues(currentL)+ " A", 1, 2));

        ansVoltageF.setText(formatter.formatString("Vf = " + formatter.notationValue(voltageF, "V"), 1, 2));
        ansCurrentF.setText(formatter.formatString("If = " + formatter.formatComplexValues(currentF)+ " A", 1, 2));

        ansResistance.setText("R = " + formatter.notationValue(resistance, getString(R.string.ohm)));
        ansInductiveReact.setText(formatter.formatString("XL = " + formatter.notationValue(inductiveReact, getString(R.string.ohm)), 1, 2));

        apparentPower.setText("S = " + formatter.formatComplexValues(powerApparent) + " VA");
        activePower.setText("P = " + formatter.notationValue(powerActive, "W"));
        reactivePower.setText("Q = " + formatter.notationValue(powerReactive, "Var"));

        if (inductance == 0) {
            ansInductiveReact.setVisibility(View.GONE);
        }
    }

}