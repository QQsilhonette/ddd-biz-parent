package org.ddd.all.app.command.query;

import org.ddd.all.client.model.dto.PmsBrandDTO;
import org.ddd.all.common.template.AbstractQuarkQueryCmdHandler;
import org.ddd.all.common.template.SimpleCmdBusTemplate;
import org.ddd.all.domain.converter.PmsBrandConverter;
import org.ddd.all.domain.repository.PmsBrandRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/21 4:47 下午
 * @description：商品品牌查询handler
 */

@Component
public class PmsBrandQryHandler {

    @Resource
    private SimpleCmdBusTemplate simpleCmdBusTemplate;
    @Resource
    private PmsBrandRepository pmsBrandRepository;
    @Resource
    private PmsBrandConverter pmsBrandConverter;

    public List<PmsBrandDTO> listAllBrand() {
        return Optional.ofNullable(pmsBrandRepository.selectAll().stream()
                .map(o -> pmsBrandConverter.entity2Dto(o))
                .collect(Collectors.toList()))
                .orElse(Collections.EMPTY_LIST);
    }
}
