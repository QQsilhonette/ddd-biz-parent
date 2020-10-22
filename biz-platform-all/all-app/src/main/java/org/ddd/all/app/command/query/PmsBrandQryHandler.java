package org.ddd.all.app.command.query;

import org.ddd.all.client.model.cmd.BrandListQry;
import org.ddd.all.client.model.dto.PmsBrandDTO;
import org.ddd.all.common.template.AbstractQuarkQueryCmdHandler;
import org.ddd.all.common.template.SimpleCmdBusTemplate;
import org.ddd.biz.platform.common.dto.QueryCommand;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/21 4:47 下午
 * @description：商品品牌查询handler
 */

@Component
public class PmsBrandQryHandler {

    @Resource
    private SimpleCmdBusTemplate simpleCmdBusTemplate;

    public List<PmsBrandDTO> listAllBrand(BrandListQry qry) {
        return simpleCmdBusTemplate.query(new AbstractQuarkQueryCmdHandler<List<PmsBrandDTO>, BrandListQry>() {

            @Override
            protected List<PmsBrandDTO> doExecute(BrandListQry var1) {
                return Collections.emptyList();
            }
        }, qry);
    }
}
