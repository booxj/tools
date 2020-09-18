package com.booxj.tools.json;

import com.booxj.tools.core.utils.ArrayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T toJavaBean(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T[] toArray(String json, Class<T> clazz) throws JsonProcessingException {
        return (T[]) objectMapper.readValue(json, ArrayUtil.newArray(clazz, 0).getClass());
    }

    public static <T> List<T> toList(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<T>>() {
        });
    }

    public static Map<String, Object> toMap(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static JsonNode toJsonNode(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }

    public static JsonNode beanToJsonNode(Object object) {
        return objectMapper.valueToTree(object);
    }

    public static <T> T jsonNodeToBean(JsonNode jsonNode, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, clazz);
    }

}
