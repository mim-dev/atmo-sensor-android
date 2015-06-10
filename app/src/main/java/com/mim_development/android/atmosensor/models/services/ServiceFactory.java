package com.mim_development.android.atmosensor.models.services;

import com.mim_development.android.atmosensor.models.services.sensor.SensorServiceImpl;

/**
 * Created by luther stanton on 6/4/15.
 */
public class ServiceFactory {
    public static SensorServiceImpl getSensorService(){
        return SensorServiceImpl.getInstance();
    }
}
