package com.pizzeria.munayco.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Slf4j
public class JsonUtil {
    private JsonUtil() {
    }

    public static String convertToJson(ResponseBase responseBase) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(responseBase);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static <T> T convertFromJson(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
