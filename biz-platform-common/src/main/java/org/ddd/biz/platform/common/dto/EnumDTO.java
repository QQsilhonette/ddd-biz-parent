package org.ddd.biz.platform.common.dto;

import java.util.Objects;

public class EnumDTO extends DTO {

    private Integer value;

    private String code;

    private String displayName;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnumDTO)) {
            return false;
        }
        EnumDTO enumDTO = (EnumDTO) o;
        return Objects.equals(value, enumDTO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EnumDTO{");
        sb.append("value=").append(value);
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
