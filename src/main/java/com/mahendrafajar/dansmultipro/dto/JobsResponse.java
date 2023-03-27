package com.mahendrafajar.dansmultipro.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class JobsResponse {
    String code;

    String message;

    Data data = new Data();

    @lombok.Data
    public static class Data{
        ArrayList<Result> result = new ArrayList<>();

        @lombok.Data
        public static class Result{
            String location;

            ArrayList<Job> data = new ArrayList<>();
        }
    }
}
