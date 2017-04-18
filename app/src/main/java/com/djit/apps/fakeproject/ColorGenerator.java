package com.djit.apps.fakeproject;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ColorGenerator {

    private int alpha;
    public static final int DEFAULT_ALPHA = 100;
    private List<ColorGenerator.OnAlphaChangeListener> listeners;

    public ColorGenerator() {
        listeners = new ArrayList<>();
    }


    public int getRandomColor() {
        Random random = new Random();
        alpha = DEFAULT_ALPHA;
        return Color.argb(getAlpha(), random.nextInt(), random.nextInt(), random.nextInt());
    }


    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int newAlpha) {
        alpha = newAlpha;
        notifyStateChange();
    }

    public void addListener(OnAlphaChangeListener listener) {
        listeners.add(listener);
    }


    public interface OnAlphaChangeListener {

        void onAlphaChanged(ColorGenerator colorGenerator);
    }

    private void notifyStateChange() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onAlphaChanged(this);
        }
    }
}
