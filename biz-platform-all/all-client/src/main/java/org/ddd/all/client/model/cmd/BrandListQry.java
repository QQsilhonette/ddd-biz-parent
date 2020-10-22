package org.ddd.all.client.model.cmd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.ddd.biz.platform.common.dto.QueryCommand;

@Getter
@Setter
@Accessors(chain = true)
public class BrandListQry extends QueryCommand {

}