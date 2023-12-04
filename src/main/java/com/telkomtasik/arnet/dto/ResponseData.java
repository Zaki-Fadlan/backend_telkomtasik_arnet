package com.telkomtasik.arnet.dto;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseData {

    public Map<String, Object> successResponse(Object data, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("status_code", status);
        return map;
    }

    public Map<String, Object> successResponseWithMeta(Object data, Object metaData, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("meta", metaData);
        map.put("status_code", status);
        return map;
    }

    // Response gagal
    public Map<String, Object> failResponse(List<String> messages, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("messages", messages);
        map.put("status_code", status);
        return map;
    }

}
