package com.wanmi.sbc.goods.api.response.suppliercommissiongoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.SupplierCommissionGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className SupplierCommissionGoodsPageResponse
 * @description TODO
 * @date 2021/9/14 19:31
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCommissionGoodsPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "代销商品")
    private MicroServicePage<SupplierCommissionGoodsVO> supplierCommissionGoodsPage;
}