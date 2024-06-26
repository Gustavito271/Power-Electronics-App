package com.example.appdidatico_tcc;

import static com.example.appdidatico_tcc.Utils.getItem;
import static com.example.appdidatico_tcc.Utils.put;
import static com.example.appdidatico_tcc.Utils.visibilityFields;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
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

public class ConverterCACC extends AppCompatActivity {

  LinearLayout boxType, boxMonoType, boxLoad;
  TextView voltage, peakVoltage, resistance, oscilation, capacitance, frequency, power, powerRMS;
  Button nextButton;
  EditText editVoltage, editPeakVoltage, editResistance, editOscilation, editCapacitance, editFrequency, editPower, editPowerRMS;
  Spinner spinnerType, spinnerWave, spinnerLoad, spinnerData;
  ImageButton backButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_converter_cacc);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    initializeComponents();
    spinnerTypeConfig();
    spinnerWaveConfig();
    spinnerLoadConfig();
    spinnerDataConfig();
    peakVoltageText();
    nextButtonConfig();
    backButtonConfig();
  }

  private void initializeComponents() {
    boxType = findViewById(R.id.box_tipo);
    boxMonoType = findViewById(R.id.box_mon_tipo);
    boxLoad = findViewById(R.id.box_carga);

    voltage = findViewById(R.id.texto_tensao);
    peakVoltage = findViewById(R.id.texto_vp);
    resistance = findViewById(R.id.texto_res);
    oscilation = findViewById(R.id.texto_delta);
    capacitance = findViewById(R.id.texto_cap);
    frequency = findViewById(R.id.texto_freq);
    power = findViewById(R.id.texto_power);
    powerRMS = findViewById(R.id.texto_powerRMS);

    editVoltage = findViewById(R.id.editTensaoCACC);
    editPeakVoltage = findViewById(R.id.editTensaoPCACC);
    editResistance = findViewById(R.id.editResCACC);
    editOscilation = findViewById(R.id.editDelta);
    editCapacitance = findViewById(R.id.editCapCACC);
    editFrequency = findViewById(R.id.editFreq);
    editPower = findViewById(R.id.editPower);
    editPowerRMS = findViewById(R.id.editPowerRMS);

    spinnerType = findViewById(R.id.tipo);
    spinnerWave = findViewById(R.id.tipo_onda);
    spinnerLoad = findViewById(R.id.carga);
    spinnerData = findViewById(R.id.dados);

    nextButton = findViewById(R.id.continuar_cacc);
    backButton = findViewById(R.id.voltar_cacc);
  }

  private void spinnerTypeConfig() {
    String[] items = new String[]{getString(R.string.Monophase), getString(R.string.Threephase)};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    spinnerType.setAdapter(adapter);

    spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerType.getSelectedItem().toString().equals(getString(R.string.Monophase))) {
          boxMonoType.setVisibility(View.VISIBLE);
          if (getItem(spinnerWave).equals(getString(R.string.CACC_halfWave))) {
            boxLoad.setVisibility(View.GONE);
          } else {
            boxLoad.setVisibility(View.VISIBLE);
          }
        } else {
          boxMonoType.setVisibility(View.GONE);
          boxLoad.setVisibility(View.VISIBLE);
        }
        peakVoltageText();
        spinnerDataConfig();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  private void spinnerWaveConfig() {
    String[] items = new String[]{getString(R.string.CACC_halfWave), getString(R.string.CACC_fullWave)};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    spinnerWave.setAdapter(adapter);

    spinnerWave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (getItem(spinnerWave).equals(getString(R.string.CACC_halfWave))) {
          boxLoad.setVisibility(View.GONE);
        } else {
          boxLoad.setVisibility(View.VISIBLE);
        }
        spinnerDataConfig();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  private void spinnerLoadConfig() {
    String[] items = new String[]{getString(R.string.RLoad), getString(R.string.RLLoad), getString(R.string.CACC_RCLoad)};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    spinnerLoad.setAdapter(adapter);

    spinnerLoad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerDataConfig();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  private void spinnerDataConfig() {
    boolean isRC = getItem(spinnerLoad).equals(getString(R.string.CACC_RCLoad));
    boolean isHalfWave = getItem(spinnerWave).equals(getString(R.string.CACC_halfWave));
    boolean isTriphase = getItem(spinnerType).equals(getString(R.string.Threephase));

    visibilityFields(resistance, editResistance, View.GONE);
    visibilityFields(voltage, editVoltage, View.GONE);
    visibilityFields(peakVoltage, editPeakVoltage, View.GONE);
    visibilityFields(oscilation, editOscilation, View.GONE);
    visibilityFields(capacitance, editCapacitance, View.GONE);
    visibilityFields(frequency, editFrequency, View.GONE);

    String[] select_qualification = isRC && (!isHalfWave || isTriphase)
      ?
        new String[]{
          getString(R.string.selecione), getString(R.string.spinner_tensaoFonte),
          getString(R.string.spinner_tensaoPico),getString(R.string.spinner_resistencia),
          getString(R.string.spinner_deltaVo), getString(R.string.spinner_capacitancia),
          getString(R.string.spinner_frequencia), getString(R.string.CACC_Power), getString(R.string.CACC_PowerRMS)}
      :
        new String[]{
          getString(R.string.selecione), getString(R.string.spinner_tensaoFonte),
          getString(R.string.spinner_tensaoPico),getString(R.string.spinner_resistencia),getString(R.string.CACC_Power), getString(R.string.CACC_PowerRMS)};

    EditText[] editTexts = isRC && (!isHalfWave || isTriphase)
        ?
          new EditText[]{
              editResistance, editVoltage, editPeakVoltage, editResistance, editOscilation,
              editCapacitance, editFrequency, editPower, editPowerRMS}
        :
          new EditText[]{
              editResistance, editVoltage, editPeakVoltage, editResistance, editPower, editPowerRMS};

    TextView[] textViews = isRC && (!isHalfWave || isTriphase)
        ?
          new TextView[]{
              resistance, voltage, peakVoltage, resistance, oscilation, capacitance, frequency,
              power, powerRMS}
        :
          new TextView[]{resistance, voltage, peakVoltage, resistance, power, powerRMS};

    ArrayList<SpinnerCheckbox> listVOs = new ArrayList<>();

    for (int i = 0; i < select_qualification.length; i++) {
      SpinnerCheckbox stateVO = new SpinnerCheckbox();
      stateVO.setTitle(select_qualification[i]);
      stateVO.setSelected(false);
      stateVO.setEditText(editTexts[i]);
      stateVO.setTextView(textViews[i]);
      listVOs.add(stateVO);
    }

    CustomAdapter myAdapter = new CustomAdapter(ConverterCACC.this, 0, listVOs);
    spinnerData.setAdapter(myAdapter);
  }

  private void peakVoltageText() {
    String text = getItem(spinnerType).equals(getString(R.string.Monophase)) ? getString(R.string.tensaoVs) : getString(R.string.tensaoVl);

    SpannableString s = new SpannableString(text);
    s.setSpan(new RelativeSizeSpan(0.65f), 18, 19, 0);

    voltage.setText(s);
  }

  private void nextButtonConfig() {
    nextButton.setOnClickListener(v -> {
      Intent intent = new Intent(ConverterCACC.this, ResultCACC.class);
      put(intent, getString(R.string.key_initialVoltage), editVoltage);
      put(intent, getString(R.string.key_voltageP), editPeakVoltage);
      put(intent, getString(R.string.key_resistance), editResistance);
      intent.putExtra(getString(R.string.key_type), getItem(spinnerType));
      intent.putExtra(getString(R.string.key_wave), getItem(spinnerWave));
      intent.putExtra(getString(R.string.key_load), getItem(spinnerLoad));
      put(intent, getString(R.string.key_voltageOscilation), editOscilation);
      put(intent, getString(R.string.key_frequency), editFrequency);
      put(intent, getString(R.string.key_capacitance), editCapacitance);
      put(intent, getString(R.string.key_power), editPower);
      put(intent, getString(R.string.key_powerRMS), editPowerRMS);

      startActivity(intent);
    });
  }

  private void backButtonConfig() {
    backButton.setOnClickListener(v -> finish());
  }

}