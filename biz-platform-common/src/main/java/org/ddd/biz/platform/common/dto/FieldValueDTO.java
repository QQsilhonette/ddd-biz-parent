package org.ddd.biz.platform.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class FieldValueDTO extends DTO {
    Object value;

    Map<String, Object> extraProps;

    public FieldValueDTO() {
    }

    public FieldValueDTO(Object value) {
        this(value, null);
    }

    public FieldValueDTO(Object value, Map<String, Object> extraProps) {
        this.value = value;
        this.extraProps = extraProps;
    }
    
}
