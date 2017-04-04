package com.djit.apps.fakeproject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Malik Toudert on 04/04/2017.
 */

public class UnitTestEnergyPlant {

    EnergyPlant energyPlant;

    @Before
    public void setUp(){

        energyPlant = new EnergyPlant();
    }

    @Test
    public void getMinVoltage(){
        int minVoltage = EnergyPlant.MIN_VOLTAGE;
        energyPlant.setVoltage(minVoltage);
        assertEquals(energyPlant.getVoltage(),10);
    }


}
