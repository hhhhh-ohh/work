package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description   凑单页运费信息
 * @author  wur
 * @date: 2022/7/5 9:21
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartListRequest extends BaseRequest {

    @Schema(description = "运费模板Id")
    @NotNull
    private List<GoodsInfoCartVO> goodsInfoCartVOList;

    @Schema(description = "运费模板Id")
    @NotNull
    private List<StoreVO> storeVOS;

    @Schema(description = "运费模板Id")
    @NotNull
    private PlatformAddress address;

}
