package org.ddd.all.client.api;

import org.ddd.all.client.model.dto.PmsBrandDTO;

import java.util.List;

/**
 * 商品品牌Service
 */
public interface PmsBrandService {
    /**
     * 获取所有品牌
     * @return
     */
    List<PmsBrandDTO> listAllBrand();
}
