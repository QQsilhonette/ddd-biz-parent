package org.ddd.biz.platform.framework.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ddd.biz.platform.framework.constants.FrameworkConstants.OBJECT_MAPPER;

@Mapper(componentModel = "spring", uses = {}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FrameworkCommonConverter {
    ObjectMapper objectMapper = new ObjectMapper();

    int ONE_HUNDRED = 100;
    int SCALE = 2;


    default Integer toInteger(Boolean booleanValue) {
        if (booleanValue) {
            return 1;
        } else {
            return 0;
        }
    }

    default Boolean toBoolean(Integer booleanValue) {
        if (booleanValue == null) {
            return Boolean.FALSE;
        }
        if (booleanValue == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }


    default Instant toInstant(Long timestamps) {
        if (timestamps == null) {
            return null;
        }
        return Instant.ofEpochMilli(timestamps);
    }

    default Long toTimestamps(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.toEpochMilli();
    }

    default Map<String, Object> toMap(String json) {
        if(StringUtils.isEmpty(json)) {
            return new HashMap<>(1);
        }
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default String toJson(Map<String, Object> map) {
        if(MapUtils.isEmpty(map)) {
            return "";
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    default List<Map> toMapList(String jsonArray) {
        try {
            return OBJECT_MAPPER.readValue(jsonArray, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Map.class));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
