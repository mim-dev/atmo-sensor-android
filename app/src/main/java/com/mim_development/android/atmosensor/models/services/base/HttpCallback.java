package com.mim_development.android.atmosensor.models.services.base;

/**
 * Created by luther stanton on 6/4/15.
 */
public interface HttpCallback <T> {

        void success(String instanceIdentifier, T result);

        void error(String instanceIdentifier, Exception e);
}
