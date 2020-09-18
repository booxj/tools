package com.booxj.tools.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * xml <-> java 转化工具类
 * <p>
 * bean 配合注解使用 根节点注解{@link JacksonXmlRootElement}, 属性注解{@link JacksonXmlProperty}, 集合注解{@link JacksonXmlElementWrapper}
 */
public class XmlUtil {

    private static ObjectMapper objectMapper = new XmlMapper();

    public static <T> T toJavaBean(String xml, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(xml, clazz);
    }

    public static JsonNode toJsonNode(String xml) throws JsonProcessingException {
        return objectMapper.readTree(xml);
    }

    public static String toXml(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
