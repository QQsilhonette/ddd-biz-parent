package org.ddd.all.domain.converter;

import org.ddd.all.client.model.dto.PmsBrandDTO;
import org.ddd.all.domain.entity.PmsBrand;
import org.ddd.all.infrastructure.dataobject.PmsBrandDO;
import org.ddd.biz.platform.framework.converter.ConverterConfig;
import org.mapstruct.Mapper;

@Mapper(config = ConverterConfig.class)
public interface PmsBrandConverter {

    PmsBrand do2Entity(PmsBrandDO brandDO);

    PmsBrandDTO entity2Dto(PmsBrand brand);
}