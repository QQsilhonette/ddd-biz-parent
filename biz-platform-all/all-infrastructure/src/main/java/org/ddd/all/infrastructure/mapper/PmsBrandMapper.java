package org.ddd.all.infrastructure.mapper;

import java.util.List;

import org.ddd.all.infrastructure.dataobject.PmsBrandDO;

/**
 * @mbg.generated
 * 表名: pms_brand
 * @date 2024/04/28
 */
public interface PmsBrandMapper {
    /**
     * @mbg.generated
     */
    int insert(PmsBrandDO record);

    /**
     * @mbg.generated
     */
    int insertSelective(PmsBrandDO record);

    /**
     * @mbg.generated
     */
    PmsBrandDO selectByPrimaryKey(Long id);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PmsBrandDO record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(PmsBrandDO record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(PmsBrandDO record);

    List<PmsBrandDO> selectAll();
}