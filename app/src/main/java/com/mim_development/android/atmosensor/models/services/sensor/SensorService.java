package com.mim_development.android.atmosensor.models.services.sensor;



import com.mim_development.android.atmosensor.models.services.base.HttpCallback;
import com.mim_development.android.atmosensor.models.services.base.Service;
import com.mim_development.android.atmosensor.models.services.sensor.responses.SensorResponse;

import java.util.List;

/**
 * Created by luther stanton on 6/4/15.
 */
public interface SensorService extends Service {

    String fetch(HttpCallback<List<SensorResponse>> callback);

}
