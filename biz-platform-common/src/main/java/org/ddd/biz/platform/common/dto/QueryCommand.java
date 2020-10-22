package org.ddd.biz.platform.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
public class QueryCommand extends Command {

    private static final long serialVersionUID = 1L;

}
