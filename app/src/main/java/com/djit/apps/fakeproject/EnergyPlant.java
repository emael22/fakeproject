package com.djit.apps.fakeproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik Toudert on 03/04/2017.
 */

public class EnergyPlant {

    private int voltage;
    public static final int MIN_VOLTAGE = 10;
    public static final int MAX_VOLTAGE = 20;
    private List<OnVoltageChangeListener> listeners;


    public EnergyPlant(int voltage) {
        this.voltage = voltage;
        listeners = new ArrayList<>();
    }

    public EnergyPlant() {
        listeners = new ArrayList<>();
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int newVoltage) {
        if (newVoltage < MIN_VOLTAGE) {
            throw new IllegalStateException("Too small voltage. Must be >= "
                    + MIN_VOLTAGE + " Found: " + newVoltage);
        }

        voltage = newVoltage;

        notifyStateChange();
    }

    public void addListener(OnVoltageChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OnVoltageChangeListener listener) {
        listeners.remove(listener);
    }

    public interface OnVoltageChangeListener {

        void onVoltageChanged(EnergyPlant energyPlant);
    }

    private void notifyStateChange() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onVoltageChanged(this);
        }
    }
}






