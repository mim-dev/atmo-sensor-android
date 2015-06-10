package com.mim_development.android.atmosensor.models.services.sensor;

import com.mim_development.android.atmosensor.Globals;
import com.mim_development.android.atmosensor.exceptions.OperationException;
import com.mim_development.android.atmosensor.models.services.base.HttpCallback;
import com.mim_development.android.atmosensor.models.services.base.OperationCallback;
import com.mim_development.android.atmosensor.models.services.base.ServiceImpl;
import com.mim_development.android.atmosensor.models.services.sensor.operations.SensorFetchOperation;
import com.mim_development.android.atmosensor.models.services.sensor.responses.SensorResponse;

import java.util.List;
import java.util.UUID;

/**
 * Created by luther stanton on 6/9/15.
 */
public class SensorServiceImpl extends ServiceImpl implements SensorService, OperationCallback {

    static SensorServiceImpl instance;

    public static SensorServiceImpl getInstance(){
        if(instance == null){
            instance = new SensorServiceImpl();
        }

        return instance;
    }

    public String fetch(HttpCallback<List<SensorResponse>> callback){

        String operationInstanceIdentifier = UUID.randomUUID().toString();
        WorkQueueValue workQueueValue = new WorkQueueValue(
                callback,
                new SensorFetchOperation(
                        Globals.URL,
                        operationInstanceIdentifier,
                        this));

        addValueToOperationWorkQueue(operationInstanceIdentifier, workQueueValue);

        return null;
    }

    @Override
    public void success(String operationInstanceIdentifier){
        WorkQueueValue workQueueValue = fetchValueFromOperationWorkQueue(operationInstanceIdentifier, true);
        if(workQueueValue != null){
            workQueueValue.callback.success(operationInstanceIdentifier, workQueueValue.operation.getResponse());
        }
    }

    @Override
    public void error(String operationInstanceIdentifier, OperationException exception){
        WorkQueueValue workQueueValue = fetchValueFromOperationWorkQueue(operationInstanceIdentifier, true);
        if(workQueueValue != null) {
            workQueueValue.callback.error(operationInstanceIdentifier, exception);
        }
    }
}
