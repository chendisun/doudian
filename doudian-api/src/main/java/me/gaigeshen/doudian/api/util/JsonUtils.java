package me.gaigeshen.doudian.api.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author gaigeshen
 */
public class JsonUtils {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  private JsonUtils() { }

  public static String toJson(Object object) {
    return toJson(object, objectMapper);
  }

  public static String toJson(Object object, ObjectMapper objectMapper) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Cannot to json from: " + object);
    }
  }

  public static JsonNode parseJsonNode(String json) {
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid json input: " + json);
    }
    return jsonNode;
  }

  public static JsonNode parseJsonNode(String json, String field) {
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid json input: " + json);
    }
    if (Objects.isNull(jsonNode) || !jsonNode.isObject()) {
      throw new IllegalArgumentException("Can only support json object, but input json is: " + json);
    }
    if (StringUtils.isNotBlank(field)) {
      jsonNode = jsonNode.get(field);
    }
    if (Objects.isNull(jsonNode)) {
      throw new IllegalArgumentException("Missing field, but input json is: " + json);
    }
    return jsonNode;
  }

  public static JsonNode parseJsonNode(JsonNode jsonNode, String field) {
    JsonNode fieldJsonNode = jsonNode.get(field);
    if (Objects.isNull(fieldJsonNode)) {
      throw new IllegalArgumentException("Missing field, but input json is: " + jsonNode);
    }
    return fieldJsonNode;
  }

  public static boolean parseBooleanValue(JsonNode jsonNode) {
    if (!jsonNode.isBoolean()) {
      throw new IllegalArgumentException("Cannot parse to boolean from: " + jsonNode);
    }
    return jsonNode.asBoolean();
  }

  public static String parseStringValue(JsonNode jsonNode) {
    if (!jsonNode.isTextual()) {
      throw new IllegalArgumentException("Cannot parse to string from: " + jsonNode);
    }
    return jsonNode.asText();
  }

  public static int parseIntValue(JsonNode jsonNode) {
    if (!jsonNode.isInt()) {
      throw new IllegalArgumentException("Cannot parse to int from: " + jsonNode);
    }
    return jsonNode.asInt();
  }

  public static <T> T parseObject(JsonNode jsonNode, Class<T> targetClass) {
    if (!jsonNode.isObject()) {
      throw new IllegalArgumentException("Can only support json object, but json input: " + jsonNode);
    }
    try {
      return objectMapper.treeToValue(jsonNode, targetClass);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Cannot parse to object from: " + jsonNode);
    }
  }

  public static <T> List<T> parseArray(JsonNode jsonNode, Class<T> targetClass) {
    if (!jsonNode.isArray()) {
      throw new IllegalArgumentException("Can only support json array, but json input: " + jsonNode);
    }
    List<T> result = new ArrayList<>();
    try {
      for (JsonNode node : jsonNode) {
        result.add(objectMapper.treeToValue(node, targetClass));
      }
      return result;
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Cannot parse to array from: " + jsonNode);
    }
  }

  public static boolean parseBooleanValue(String json, String field) {
    return parseBooleanValue(parseJsonNode(json, field));
  }

  public static String parseStringValue(String json, String field) {
    return parseStringValue(parseJsonNode(json, field));
  }

  public static int parseIntValue(String json, String field) {
    return parseIntValue(parseJsonNode(json, field));
  }

  public static <T> T parseObject(String json, String field, Class<T> targetClass) {
    return parseObject(parseJsonNode(json, field), targetClass);
  }

  public static <T> List<T> parseArray(String json, String field, Class<T> targetClass) {
    return parseArray(parseJsonNode(json, field), targetClass);
  }

  public static boolean parseBooleanValue(JsonNode jsonNode, String field) {
    return parseBooleanValue(parseJsonNode(jsonNode, field));
  }

  public static String parseStringValue(JsonNode jsonNode, String field) {
    return parseStringValue(parseJsonNode(jsonNode, field));
  }

  public static int parseIntValue(JsonNode jsonNode, String field) {
    return parseIntValue(parseJsonNode(jsonNode, field));
  }

  public static <T> T parseObject(JsonNode jsonNode, String field, Class<T> targetClass) {
    return parseObject(parseJsonNode(jsonNode, field), targetClass);
  }

  public static <T> List<T> parseArray(JsonNode jsonNode, String field, Class<T> targetClass) {
    return parseArray(parseJsonNode(jsonNode, field), targetClass);
  }
}
