package org.ddd.all.infrastructure.mapper;

import java.util.List;
import org.ddd.all.infrastructure.dataobject.PmsBrandDO;

/**
 * @mbg.generated
 * 表名: pms_brand
 * @date 2020/10/23
 */
public interface PmsBrandMapper {
    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @mbg.generated
     */
    int insert(PmsBrandDO record);

    /**
     * @mbg.generated
     */
    PmsBrandDO selectByPrimaryKey(Long id);

    /**
     * @mbg.generated
     */
    List<PmsBrandDO> selectAll();

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(PmsBrandDO record);
}