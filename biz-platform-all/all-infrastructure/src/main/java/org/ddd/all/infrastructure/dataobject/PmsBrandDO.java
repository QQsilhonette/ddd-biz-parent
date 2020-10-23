package org.ddd.all.infrastructure.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.ddd.biz.platform.framework.dao.AbstractDataObject;

/**
 * @mbg.generated
 * 表名: pms_brand
 * @date 2020/10/23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PmsBrandDO extends AbstractDataObject {
    /**
     * @mbg.generated
     * 平台值
     * 表字段: platform
     */
    private Integer platform;

    /**
     * @mbg.generated
     * 租户id
     * 表字段: tenant_id
     */
    private String tenantId;

    /**
     * @mbg.generated
     * 品牌名
     * 表字段: name
     */
    private String name;

    /**
     * @mbg.generated
     * 首字母
     * 表字段: first_letter
     */
    private String firstLetter;

    /**
     * @mbg.generated
     * 排序
     * 表字段: sort
     */
    private Integer sort;

    /**
     * @mbg.generated
     * 是否为品牌制造商：0->不是；1->是
     * 表字段: factory_status
     */
    private Integer factoryStatus;

    /**
     * @mbg.generated
     * 是否显示
     * 表字段: show_status
     */
    private Integer showStatus;

    /**
     * @mbg.generated
     * 产品数量
     * 表字段: product_count
     */
    private Integer productCount;

    /**
     * @mbg.generated
     * 产品评论数量
     * 表字段: product_comment_count
     */
    private Integer productCommentCount;

    /**
     * @mbg.generated
     * 品牌logo
     * 表字段: logo
     */
    private String logo;

    /**
     * @mbg.generated
     * 专区大图
     * 表字段: big_pic
     */
    private String bigPic;

    /**
     * @mbg.generated
     * 品牌故事
     * 表字段: brand_story
     */
    private String brandStory;
}