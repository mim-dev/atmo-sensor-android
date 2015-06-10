package com.mim_development.android.atmosensor.models.services.sensor.operations;

import com.mim_development.android.atmosensor.models.services.base.HttpOperationImpl;
import com.mim_development.android.atmosensor.models.services.base.OperationCallback;

import java.util.Map;

/**
 * Created by luther stanton on 6/9/15.
 */
public class SensorFetchOperation extends HttpOperationImpl {

    public SensorFetchOperation(
            String url,
            String operationInstanceIdentifier,
            OperationCallback operationCallback) {
        super(url, operationInstanceIdentifier, operationCallback);
    }

    @Override
    protected String getRequestPayload() {
        return null;
    }

    @Override
    protected Map<String, String> getHeaders() {
        return null;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return null;
    }

    @Override
    protected Map<String, String> getQueryString() {
        return null;
    }

    @Override
    protected String getUriAction() {
        return null;
    }

    @Override
    protected Object mapResponse(String jsonResponseContent) {
        return null;
    }
}
