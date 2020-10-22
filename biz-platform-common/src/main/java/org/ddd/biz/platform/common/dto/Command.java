package org.ddd.biz.platform.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
public abstract class Command extends DTO {

    private static final long serialVersionUID = 1L;

    /**
     * 语言
     */
    protected String lang;
}
