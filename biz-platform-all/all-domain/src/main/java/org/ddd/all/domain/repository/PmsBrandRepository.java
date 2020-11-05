package org.ddd.all.domain.repository;

import org.ddd.all.domain.converter.PmsBrandConverter;
import org.ddd.all.domain.entity.PmsBrand;
import org.ddd.all.infrastructure.mapper.PmsBrandMapper;
import org.ddd.biz.platform.framework.ddd.DomainRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/21 4:58 下午
 * @description：商品品牌Repository
 */
@Repository
public class PmsBrandRepository extends DomainRepository<PmsBrand> {

    @Resource
    private PmsBrandMapper pmsBrandMapper;
    @Resource
    private PmsBrandConverter pmsBrandConverter;

    @Override
    public Optional<PmsBrand> findOneById(Object id) {
        return Optional.empty();
    }

    @Override
    public boolean exists(PmsBrand pmsBrand) {
        return false;
    }

    @Override
    protected void add(PmsBrand pmsBrand) {

    }

    @Override
    protected void update(PmsBrand pmsBrand) {

    }

    public List<PmsBrand> selectAll() {
        return Optional.ofNullable(pmsBrandMapper.selectAll())
                .orElse(new ArrayList<>())
                .stream().map(o -> pmsBrandConverter.do2Entity(o))
                .collect(Collectors.toList());
    }
}
