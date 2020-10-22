package org.ddd.all.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.ddd.all.domain.repository.PmsBrandRepository;
import org.ddd.biz.platform.framework.ddd.Entity;
import org.springframework.beans.factory.annotation.Autowired;

@ToString
@Accessors(chain = true)
@Getter
@Setter
public class PmsBrand extends Entity<Long> {

    @Autowired
    private PmsBrandRepository pmsBrandRepository;

    private String name;

    /**
     * 首字母
     */
    private String firstLetter;

    private Integer sort;

    /**
     * 是否为品牌制造商：0->不是；1->是
     */
    private Integer factoryStatus;

    private Integer showStatus;

    /**
     * 产品评论数量
     */
    private Integer productCount;

    /**
     * 产品评论数量
     */
    private Integer productCommentCount;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 专区大图
     */
    private String bigPic;

    /**
     * 品牌故事
     */
    private String brandStory;

    @Override
    public void persist() {
        pmsBrandRepository.persist(this);
    }
}