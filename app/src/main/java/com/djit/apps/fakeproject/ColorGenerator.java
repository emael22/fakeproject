package com.djit.apps.fakeproject;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;


public class ColorGenerator {

    public int blue;
    public int green;
    public int red;

    public static final int DEFAULT_BLUE = 100;
    public static final int DEFAULT_GREEN = 100;
    public static final int DEFAULT_RED = 100;
    private List<OnColorChangeListener> listeners;

    public ColorGenerator() {
        listeners = new ArrayList<>();
    }


    public int getDefaultColor() {
        red = DEFAULT_RED;
        green = DEFAULT_GREEN;
        blue = DEFAULT_BLUE;

        return Color.argb(255, red, green, blue);
    }

    public int getNewColor(){
        return Color.argb(255, red, green, blue);
    }



    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int newRed) {
        red = newRed;
        notifyStateChange();
    }

    public void setGreen(int newGreen) {
        green = newGreen;
        notifyStateChange();
    }

    public void setBlue(int newBlue) {
        blue = newBlue;
        notifyStateChange();
    }

    public void addListener(OnColorChangeListener listener) {
        listeners.add(listener);
    }

    public interface OnColorChangeListener {

        void onColorChanged(ColorGenerator colorGenerator);
    }

    private void notifyStateChange() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onColorChanged(this);
        }
    }
}
