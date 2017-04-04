package com.djit.apps.fakeproject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UnitTestEnergyPlant {

    EnergyPlant energyPlant;

    @Before
    public void setUp() {
        energyPlant = new EnergyPlant();

    }

    @Test
    public void setNewVoltage() {
        energyPlant.setVoltage(EnergyPlant.MIN_VOLTAGE);
        assertEquals(energyPlant.getVoltage(), EnergyPlant.MIN_VOLTAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void throwIfTooSmallVoltage() {
        energyPlant.setVoltage(EnergyPlant.MIN_VOLTAGE - 1);
    }


    @Test
    public void notifyListener() {
        // setup
        final CaptureResult captureResult = new CaptureResult();
        energyPlant.addListener(new EnergyPlant.OnVoltageChangeListener() {
            @Override
            public void onVoltageChanged(EnergyPlant energyPlant) {
                captureResult.capture(energyPlant.getVoltage());
            }
        });

        // Act
        energyPlant.setVoltage(EnergyPlant.MIN_VOLTAGE);

        // Assert
        assertEquals(captureResult.getValue(), EnergyPlant.MIN_VOLTAGE);
    }

    @Test
    public void notifyListenerWithMock() {
        final int newVoltage = EnergyPlant.MIN_VOLTAGE;
        EnergyPlant.OnVoltageChangeListener mock = mock(EnergyPlant.OnVoltageChangeListener.class);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                int voltage = ((EnergyPlant) invocation.getArguments()[0]).getVoltage();
                if (voltage != newVoltage) {
                    throw new IllegalStateException("");
                }
                return null;
            }
        }).when(mock).onVoltageChanged(energyPlant);

        energyPlant.addListener(mock);

        // Act
        energyPlant.setVoltage(newVoltage);

        // Assert
        verify(mock).onVoltageChanged(energyPlant);
    }

    private static class CaptureResult {
        private int value;

        public void capture(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
