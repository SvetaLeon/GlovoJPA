package com.company.glovojpa.controller.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResponse<D> {
    private boolean success;
    private D data;
    private List<String> messages;

    public ApiResponse(boolean success, D data, List<String> messages) {
        this.success = success;
        this.data = data;
        this.messages = messages != null ? messages : new ArrayList<>();
    }
}
