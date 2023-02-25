package com.mahendrafajar.dansmultipro.dto;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.InputStream;

@Data
public class HandleErrorData {
    private InputStream responseBody;
    private HttpHeaders responseHeader;
    private HttpStatus httpResponseCode;
}
