package com.mahendrafajar.dansmultipro.configurations;

import com.mahendrafajar.dansmultipro.dto.HandleErrorData;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class HttpErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HandleErrorData handleErrorPOJO = new HandleErrorData();
        handleErrorPOJO.setHttpResponseCode(response.getStatusCode());
        handleErrorPOJO.setResponseHeader(response.getHeaders());
        handleErrorPOJO.setResponseBody(response.getBody());
    }

}