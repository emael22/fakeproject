package com.djit.apps.fakeproject;

/**
 * Created by Malik Toudert on 03/04/2017.
 */

public class EnergyPlant {

    public int voltage;
    public static final int MIN_VOLTAGE = 10;
    public static final int MAX_VOLTAGE = 50;


    public EnergyPlant(int voltage) {
        this.voltage = voltage;
    }

    public EnergyPlant() {

    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int newVoltage) {
        voltage = newVoltage;
        notifyStateChange();

    }

    private OnVoltageChangeListener listener;

    public void setOnVoltageChangeListener(OnVoltageChangeListener listener) {
        this.listener = listener;
    }

    public interface OnVoltageChangeListener {

        void onVoltageChanged(EnergyPlant energyPlant);
    }

    private void notifyStateChange() {
        if (listener != null) {
            listener.onVoltageChanged(this);
        }
    }
}






