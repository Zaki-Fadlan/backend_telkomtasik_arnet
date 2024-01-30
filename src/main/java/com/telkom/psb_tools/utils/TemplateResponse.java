package com.telkom.psb_tools.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateResponse {
    public Map successTemplate(Object object){
        Map map = new HashMap();
        map.put("data", object);
        map.put("message", "Sukses");
        map.put("status", "200");
        return map;
    }
    public Map errorTemplate(Object object){
        Map map = new HashMap();
        map.put("message", object);
        map.put("status", "404");
        return map;
    }

    public boolean checkNull(Object obj) {
        return obj == null;
    }
}
