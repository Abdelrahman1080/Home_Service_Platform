package org.example.userservice.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static Map<String, Object> success(String message, Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "success");
        res.put("message", message);
        res.put("data", data);
        return res;
    }

    public static Map<String, Object> error(String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "error");
        res.put("message", message);
        return res;
    }
}