package com.appcenter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EventUtil {
    public static <T> T mappingMessageToClass(String message, Class<T> classType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(message, classType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
