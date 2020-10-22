package org.ddd.biz.platform.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
public class PlatformQueryCommand extends QueryCommand {

    private static final long serialVersionUID = 1L;

    @NotNull
    protected Integer platform;

    @NotNull
    protected String tenantId;
}
