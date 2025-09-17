package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.goods.bean.vo.FreightGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;


/**
 * @description   凑单页运费信息
 * @author  wur
 * @date: 2022/7/5 9:21
 **/
@Schema
@Data
public class CollectPageInfoRequest extends BaseRequest {

    @Schema(description = "运费模板Id")
    @NotNull
    private Long freightTemplateId;

    @Schema(description = "免邮规则Id")
    private Long freeId;

    @Schema(description = "使用的运费模板类别(0:店铺运费,1:单品运费)")
    @NotNull
    private DefaultFlag freightTemplateType;

    @Schema(description = "店铺Id")
    @NotNull
    private Long storeId;

    @Schema(description = "供应商店铺Id")
    private Long providerStoreId;

    @Schema(description = "选中商品信息Id 和freightGoodsInfoVOList 同时使用优先使用")
    private List<String> goodsInfoIds;

    @Schema(description = "命中商品信息")
    private List<FreightGoodsInfoVO> freightGoodsInfoVOList;

    @Schema(description = "客户信息 - 无需前端传入")
    private CustomerDTO customer;

}
