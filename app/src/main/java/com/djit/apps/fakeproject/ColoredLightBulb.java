package com.djit.apps.fakeproject;

/**
 * A light bulb that has a color.
 * <p>
 * <b>Note:</b> the default color is {@link ColoredLightBulb#DEFAULT_COLOR}
 * <p>
 * <b>Note:</b> when turned off, the bulb does not save it's current color.
 * The current color is reset to {@link ColoredLightBulb#DEFAULT_COLOR}.
 */
public class ColoredLightBulb extends EnergyPlant {

    /**
     * The default color of {@link ColoredLightBulb}.
     */
    public static final int DEFAULT_COLOR = 0xFFFFFF;
    public static final boolean DEFAULT_STATE_ON = false;



    private final ColorGenerator colorGenerator;
    private final EnergyPlant energyPlant;

    /**
     * The boolean representing the internal state of this {@link ColoredLightBulb}.
     */
    private boolean turnedOn;
    /**
     * The current color of this {@link ColoredLightBulb}.
     */
    private int color;

    private OnColoredLightBulbStateChangeListener listener;

    /**
     * Create a {@link ColoredLightBulb} that will have
     * the {@link ColoredLightBulb#DEFAULT_COLOR} as color.
     * <p>
     * <b>Note:</b> By default the bulb is turned off.
     */
    public ColoredLightBulb(ColorGenerator colorGenerator, EnergyPlant energyPlant, int lightBulbColor, boolean bulbStateOn, int voltage) {
        this(colorGenerator, energyPlant, DEFAULT_COLOR, false);
    }

    public ColoredLightBulb(ColorGenerator generator,
                            EnergyPlant energyPlant,
                            int color,
                            boolean turnedOn) {
        this.energyPlant = energyPlant;
        this.colorGenerator = generator;
        this.color = color;
        this.turnedOn = turnedOn;
    }


    /**
     * Turn this {@link ColoredLightBulb} ON.
     *
     * @throws IllegalStateException if the {@link EnergyPlant} has a voltage
     *                               higher than {@link ColoredLightBulb#MAX_VOLTAGE}
     */
    public boolean turnOn() {
        if (energyPlant.getVoltage() < MIN_VOLTAGE) {
            return false;
        } else if (energyPlant.getVoltage() > MAX_VOLTAGE) {
            throw new IllegalStateException("Voltage too high. Overloading risks.");
        }

        turnedOn = true;
        setColor(colorGenerator.getRandomColor());
        notifyStateChange();
        return true;
    }

    /**
     * Check if this {@link ColoredLightBulb} is turned ON.
     *
     * @return Returns true if turned on, false otherwise.
     */
    public boolean isTurnedOn() {
        return turnedOn;
    }

    /**
     * Turn this {@link ColoredLightBulb} OFF.
     * <p>
     * <b>Note:</b> turning off the bulb reset it's
     * color to the {@link ColoredLightBulb#DEFAULT_COLOR}
     *
     * @throws IllegalStateException if already turned off.
     */
    public void turnOff() {
        if (!turnedOn) {
            throw new IllegalStateException("Cannot be turned off. Already off");
        }

        turnedOn = false;
        color = DEFAULT_COLOR;
        notifyStateChange();
    }

    /**
     * Get the current color of this {@link ColoredLightBulb}.
     *
     * @return an int representing the current color.
     */
    public int getColor() {
        return color;
    }

    /**
     * Set the color of this {@link ColoredLightBulb}.
     *
     * @param color the new color.
     */
    public void setColor(int color) {
        if (!turnedOn) {
            throw new IllegalStateException("Cannot set the color when turned off. " +
                    "Please call turnOn before calling set Color.");
        }

        this.color = color;
        notifyStateChange();
    }


    public void setOnLightBulbStateChangeListener(OnColoredLightBulbStateChangeListener listener) {
        this.listener = listener;
    }

    private void notifyStateChange() {
        if (listener != null) {
            listener.onLightBulbStateChanged(this);
        }
    }


    public interface OnColoredLightBulbStateChangeListener {
        /**
         * Called when a light bulb state has just changed.
         *
         * @param lightBulb the light bulb whose state changed.
         */
        void onLightBulbStateChanged(ColoredLightBulb lightBulb);
    }


}