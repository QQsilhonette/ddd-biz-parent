package org.ddd.all.infrastructure.mapper.dataobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.ddd.biz.platform.framework.dao.AbstractDataObject;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/21 5:34 下午
 * @description：商品品牌DO
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PmsBrandDO extends AbstractDataObject {

    private String name;

    private String firstLetter;

    private Integer sort;

    private Integer factoryStatus;

    private Integer showStatus;

    private Integer productCount;

    private Integer productCommentCount;

    private String logo;

    private String bigPic;

    private String brandStory;
}
