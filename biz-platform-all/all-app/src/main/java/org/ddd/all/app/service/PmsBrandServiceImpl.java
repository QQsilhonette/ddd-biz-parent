package org.ddd.all.app.service;

import org.ddd.all.app.command.query.PmsBrandQryHandler;
import org.ddd.all.client.api.PmsBrandService;
import org.ddd.all.client.model.cmd.BrandListQry;
import org.ddd.all.client.model.dto.PmsBrandDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Resource
    private PmsBrandQryHandler pmsBrandQryHandler;

    @Override
    public List<PmsBrandDTO> listAllBrand() {
        return pmsBrandQryHandler.listAllBrand(new BrandListQry());
    }
}