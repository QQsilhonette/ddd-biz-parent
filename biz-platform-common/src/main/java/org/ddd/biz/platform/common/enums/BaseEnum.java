package org.ddd.biz.platform.common.enums;

import java.io.Serializable;

public interface BaseEnum extends Serializable {

    Integer getValue();

    String getName();

    String getDesc();

    default Class getEnumClass() {
        return this.getClass();
    }
}
