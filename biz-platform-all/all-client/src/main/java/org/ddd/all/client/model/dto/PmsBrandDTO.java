package org.ddd.all.client.model.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/21 5:34 下午
 * @description：商品品牌O
 */

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class PmsBrandDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    //    @ApiModelProperty(value = "首字母")
    private String firstLetter;

    private Integer sort;

    //    @ApiModelProperty(value = "是否为品牌制造商：0->不是；1->是")
    private Integer factoryStatus;

    private Integer showStatus;

    //    @ApiModelProperty(value = "产品数量")
    private Integer productCount;

    //    @ApiModelProperty(value = "产品评论数量")
    private Integer productCommentCount;

    //    @ApiModelProperty(value = "品牌logo")
    private String logo;

    //    @ApiModelProperty(value = "专区大图")
    private String bigPic;

    //    @ApiModelProperty(value = "品牌故事")
    private String brandStory;
}
