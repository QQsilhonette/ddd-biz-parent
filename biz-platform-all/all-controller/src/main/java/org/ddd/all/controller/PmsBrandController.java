package org.ddd.all.controller;

import org.ddd.all.client.api.PmsBrandService;
import org.ddd.all.client.model.dto.PmsBrandDTO;
import org.ddd.biz.platform.common.api.CommonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：luoqi/02216
 * @date ：Created in 2020/10/21 3:55 下午
 * @description：品牌功能controller
 */


@Controller
@RequestMapping("/brand")
public class PmsBrandController {

    @Resource
    private PmsBrandService brandService;

    @RequestMapping(value="listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrandDTO>> getBrandList() {
        return CommonResult.success(brandService.listAllBrand());
    }
}
