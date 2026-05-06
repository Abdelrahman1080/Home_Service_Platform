package org.example.bookingservice.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static Object success(Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "success");
        res.put("data", data);
        return res;
    }

    public static Object error(String msg) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "error");
        res.put("message", msg);
        return res;
    }
}
