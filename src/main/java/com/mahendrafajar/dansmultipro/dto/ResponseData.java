package com.mahendrafajar.dansmultipro.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseData<T> {
    boolean status;
    List<String> messages = new ArrayList<>();
    T data;
}
