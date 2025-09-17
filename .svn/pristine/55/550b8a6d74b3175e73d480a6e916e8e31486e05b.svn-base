package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 10:18 AM 2018/9/12
 * @Description: 优惠券信息
 */
@Schema
@Data
public class CouponInfoForScopeNamesVO extends BasicResponse {

    private static final long serialVersionUID = 6066706588520711175L;

    /**
     * 优惠券主键Id
     */
    @Schema(description = "优惠券主键Id")
    private String couponId;

    /**
     * 指定商品-只有scopeName
     */
    @Schema(description = "关联的商品范围名称集合，如分类名、品牌名")
    private  List<String> scopeNames = new ArrayList<>();



}
