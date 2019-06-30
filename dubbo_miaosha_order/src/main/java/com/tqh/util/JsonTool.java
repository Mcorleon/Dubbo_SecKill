package com.tqh.util;

import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mortbay.util.ajax.JSON;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * Create by weilong on 2018/06/08.
 **/
public class JsonTool {
    // Object转json
    public static String objectToJson(Object o) {
        final ObjectMapper om = new ObjectMapper();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            om.setDateFormat(simpleDateFormat);
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String objectToJson(Object o, String format) {
        final ObjectMapper om = new ObjectMapper();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            om.setDateFormat(simpleDateFormat);
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // json转List
    public static List<Map<String, String>> jsonToListMap(String json) throws IOException {
        final ObjectMapper om = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        om.setDateFormat(simpleDateFormat);
        return om.readValue(json, new TypeReference<List<Map<String, String>>>() {
        });
    }

    // Bean转map
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    //jsonString转map
    public static <T> Map<String, Object> jsonStringToMap(String str) {
        Map<String, Object> jsonMap = (Map) JSON.parse(str);
        return jsonMap;
    }
}
