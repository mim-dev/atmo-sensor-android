package com.mim_development.android.atmosensor.models.services.base;


import com.mim_development.android.atmosensor.exceptions.OperationException;

/**
 * Created by luther stanton on 6/4/15.
 */
public interface OperationCallback {

    void success(String operationInstanceIdentifier);
    void error(String operationInstanceIdentifier, OperationException exception);

}
