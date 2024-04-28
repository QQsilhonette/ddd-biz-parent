package org.ddd.all.generator;

import org.ddd.all.infrastructure.dataobject.PmsBrandDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PmsBrandRepositoryTest extends BaseDaoTest{

    @Test
    void test() {
        PmsBrandDO pmsBrand = new PmsBrandDO()
                .setName("name")
                .setFirstLetter("A")
                .setSort(1)
                .setFactoryStatus(1)
                .setShowStatus(1)
                .setProductCount(1)
                .setProductCommentCount(1)
                .setLogo("logo")
                .setBigPic("bigPic")
                .setBrandStory("brandStory")
                .setPlatform(1)
                .setTenantId("tenantId");
        pmsBrandMapper.insertSelective(pmsBrand);
        PmsBrandDO pmsBrandDO = pmsBrandMapper.selectByPrimaryKey(pmsBrand.getId());
        Assertions.assertNotNull(pmsBrandDO);
    }
}
