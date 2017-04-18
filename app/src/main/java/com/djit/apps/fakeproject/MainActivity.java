package com.djit.apps.fakeproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.djit.apps.fakeproject.EnergyPlant.OnVoltageChangeListener;

import static com.djit.apps.fakeproject.EnergyPlant.MAX_VOLTAGE;
import static com.djit.apps.fakeproject.EnergyPlant.MIN_VOLTAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ColoredLightBulb.OnColoredLightBulbStateChangeListener, OnVoltageChangeListener, ColorGenerator.OnAlphaChangeListener {

    public ColoredLightBulb lightBulb;
    private ColorGenerator colorGenerator;
    private EnergyPlant energyPlant;
    private TextView tvBubbleState, colorView, voltageView;
    private Button btnTurnOn, btnTurnOff, btnVoltageUp, btnVoltageDown;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find Views
        layout = (LinearLayout) findViewById(R.id.activity_main);
        tvBubbleState = (TextView) findViewById(R.id.activity_main_tv_bulb_state);
        btnTurnOn = (Button) findViewById(R.id.main_activity_btn_turn_on);
        btnTurnOff = (Button) findViewById(R.id.main_activity_btn_turn_off);
        btnVoltageUp = (Button) findViewById(R.id.main_activity_btn_voltage_up);
        btnVoltageDown = (Button) findViewById(R.id.main_activity_btn_voltage_down);

        btnTurnOn.setOnClickListener(this);
        btnTurnOff.setOnClickListener(this);
        btnVoltageUp.setOnClickListener(this);
        btnVoltageDown.setOnClickListener(this);

        btnTurnOn.setText(R.string.bulb_on);

        energyPlant = new EnergyPlant();
        energyPlant.setVoltage(MIN_VOLTAGE);

        colorGenerator = new ColorGenerator();
        colorGenerator.getRandomColor();


        lightBulb = restoreLightBulb(colorGenerator);
        lightBulb.setOnLightBulbStateChangeListener(this);
        energyPlant.addListener(this);
        colorGenerator.addListener(this);

        voltageView = (TextView) findViewById(R.id.activity_main_tv_bulb_voltage);

        colorView = (TextView) findViewById(R.id.activity_main_tv_bulb_color);

        synchronizeLightBulbState(lightBulb);
    }

    @Override
    public void onLightBulbStateChanged(ColoredLightBulb lightBulb) {
        saveLightBulb(lightBulb);
        synchronizeLightBulbState(lightBulb);
    }

    @Override
    public void onVoltageChanged(EnergyPlant energyPlant) {
        synchronizeLightBulbState(lightBulb);
    }

    @Override
    public void onAlphaChanged(ColorGenerator colorGenerator){
        synchronizeLightBulbState(lightBulb);
    }

    private void synchronizeLightBulbState(ColoredLightBulb lightBulb) {
        int lightBulbColor = lightBulb.getColor();
        String voltage = Integer.toString(energyPlant.getVoltage());
        String colorHexCode = Integer.toHexString(lightBulbColor);

        layout.setBackgroundColor(lightBulbColor);
        colorView.setText(colorHexCode);
        voltageView.setText(voltage);

        if (lightBulb.isTurnedOn()) {
            btnTurnOn.setText(R.string.change_color);
            tvBubbleState.setText(R.string.bulb_on);
            btnTurnOff.setEnabled(true);
        } else {
            btnTurnOn.setText(R.string.turn_on);
            tvBubbleState.setText(R.string.bulb_off);
            btnTurnOff.setEnabled(false);
            btnTurnOn.setEnabled(true);
        }

        boolean isBtnDownEnabled = energyPlant.getVoltage() > MIN_VOLTAGE;
        btnVoltageDown.setEnabled(isBtnDownEnabled);

        boolean isBtnUpEnabled = energyPlant.getVoltage() < MAX_VOLTAGE;
        btnVoltageUp.setEnabled(isBtnUpEnabled);
    }


    private void saveLightBulb(ColoredLightBulb lightBulb) {
        SharedPreferences sharedPref = getSharedPreferences("light_bulb_state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("bulb_state_on", lightBulb.isTurnedOn());
        editor.putInt("color_pref", lightBulb.getColor());
        editor.apply();
    }

    private ColoredLightBulb restoreLightBulb( ColorGenerator colorGenerator) {
        SharedPreferences sharedPref = getSharedPreferences("light_bulb_state", Context.MODE_PRIVATE);

        int lightBulbColor = sharedPref.getInt("color_pref", ColoredLightBulb.DEFAULT_COLOR);
        boolean bulbStateOn = sharedPref.getBoolean("bulb_state_on", ColoredLightBulb.DEFAULT_STATE_ON);

        return new ColoredLightBulb(colorGenerator, energyPlant, lightBulbColor, bulbStateOn);
    }

    public int voltageUp() {
        return energyPlant.getVoltage() + 1;
    }

    public int voltageDown() {
        return energyPlant.getVoltage() - 1;
    }

    public int alphaUp(){
        return colorGenerator.getAlpha() + 10;
    }

    public int alphaDown(){
        return colorGenerator.getAlpha() - 10;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_activity_btn_turn_on:
                lightBulb.turnOn();
                break;

            case R.id.main_activity_btn_turn_off:
                lightBulb.turnOff();
                break;

            case R.id.main_activity_btn_voltage_up:
                energyPlant.setVoltage(voltageUp());
                colorGenerator.setAlpha(alphaUp());
                break;

            case R.id.main_activity_btn_voltage_down:
                energyPlant.setVoltage(voltageDown());
                colorGenerator.setAlpha(alphaDown());
                break;

        }

    }

}

