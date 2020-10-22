package org.ddd.biz.platform.framework.converter;

import org.ddd.biz.platform.common.enums.*;
import org.ddd.biz.platform.common.dto.EnumDTO;
import org.ddd.biz.platform.framework.ddd.EntityStatus;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", uses = {}, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FrameworkEnumConverter {

    default EntityStatus toEntityStatus(Long status) {
        if (status > 0) {
            return EntityStatus.DELETED;
        }
        return EntityStatus.PERSISTED;
    }

    default Integer toValue(BaseEnum baseEnum) {
        return baseEnum.getValue();
    }


    default EnumDTO toEnumDTO(BaseEnum baseEnum) {
        EnumDTO enumDTO = new EnumDTO();
        enumDTO.setValue(baseEnum.getValue());
        enumDTO.setCode(baseEnum.getName());
        enumDTO.setDisplayName(baseEnum.getDesc());
        return enumDTO;
    }
}
