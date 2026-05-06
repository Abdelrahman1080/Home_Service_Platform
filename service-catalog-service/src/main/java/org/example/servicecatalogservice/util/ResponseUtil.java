package org.example.servicecatalogservice.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static Map<String, Object> success(Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "success");
        res.put("data", data);
        return res;
    }
}