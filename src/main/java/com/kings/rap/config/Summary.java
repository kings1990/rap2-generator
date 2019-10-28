package com.kings.rap.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Summary {
    private String bodyOption;
    private String requestParamsType;

    public enum  BodyOption {
        FORM_DATA,
        X_WWW_FORM_URLENCODED,
        RAW,
        BINARY
    }

    public enum RequestParamsType {
        BODY_PARAMS,
        QUERY_PARAMS
    }
}


