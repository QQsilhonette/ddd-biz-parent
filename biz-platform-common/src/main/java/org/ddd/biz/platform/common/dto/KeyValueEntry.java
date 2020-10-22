package org.ddd.biz.platform.common.dto;

import java.util.Objects;

public class KeyValueEntry extends DTO {
    private static final long serialVersionUID = 1L;

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public KeyValueEntry setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public KeyValueEntry setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyValueEntry)) {
            return false;
        }
        KeyValueEntry that = (KeyValueEntry) o;
        return key.equals(that.key) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("KeyValueEntry{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
