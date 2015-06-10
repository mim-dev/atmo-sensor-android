package com.mim_development.android.atmosensor.models.services.base;

import android.util.Log;

import com.mim_development.android.atmosensor.Globals;
import com.mim_development.android.atmosensor.exceptions.HttpException;
import com.mim_development.android.atmosensor.exceptions.OperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by luther stanton on 6/4/15.
 */
public abstract class HttpOperationImpl<T> implements HttpOperation<T>, Runnable {

    private static final String TAG = HttpOperationImpl.class.getCanonicalName();

    // TODO: implement toString() to correctly convert to String representation
    protected enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    protected T response;
    private String url;
    private String operationInstanceIdentifier;

    private OperationCallback operationCallback;

    protected HttpOperationImpl(final String url,
                                String operationInstanceIdentifier,
                                OperationCallback operationCallback) {

        this.url = url;
        this.operationInstanceIdentifier = operationInstanceIdentifier;
        this.operationCallback = operationCallback;
    }

    public T getResponse() {
        return response;
    }

    @Override
    public void run() {
        try {

            String protocol = Globals.USE_SECURE ? "https://" : "http://";

            String uri = protocol + this.url + "/" + getUriAction();

            Log.d(TAG, "Connecting to [" + uri + "]");

            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(getRequestMethod().toString());
            Map headers = getHeaders();

            for (Object headerName : headers.keySet()) {
                connection.setRequestProperty((String) headerName, (String) headers.get(headerName));
            }

            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                if (connection.getContentType().contains("application/json")) {
                    // get the json response
                    int contentLength = connection.getContentLength();
                    InputStream connectionStream = connection.getInputStream();
                    String jsonResponseContent = readStreamToString(connectionStream, contentLength);

                    // map the response to T
                    response = mapResponse(jsonResponseContent);

                    // send the results back to the caller
                    operationCallback.success(operationInstanceIdentifier);
                }
            } else {
                // get the json response
                int contentLength = connection.getContentLength();
                InputStream connectionStream = connection.getInputStream();
                String errorResponseContent = readStreamToString(connectionStream, contentLength);
                operationCallback.error(operationInstanceIdentifier, new HttpException(responseCode, errorResponseContent));
            }

        } catch (IOException e) {
            operationCallback.error(operationInstanceIdentifier, new OperationException(e));
        }
    }

    protected abstract String getRequestPayload();

    protected abstract Map<String, String> getHeaders();

    protected abstract RequestMethod getRequestMethod();

    protected abstract Map<String, String> getQueryString();

    protected abstract String getUriAction();

    protected abstract T mapResponse(String jsonResponseContent);


    private String readStreamToString(InputStream srcStream, int length) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(srcStream));
            StringBuilder builder = new StringBuilder(length);
            char[] buffer = new char[length];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, charsRead);
            }
            return builder.toString();
        } finally {
            srcStream.close();
        }
    }
}