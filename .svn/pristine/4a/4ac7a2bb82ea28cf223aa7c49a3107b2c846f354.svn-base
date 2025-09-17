package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.DistributionGoodsInfoModifyDTO;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.info.DistributionGoodsAddRequest
 * 添加分销商品请求对象
 *
 * @author CHENLI
 * @dateTime 2019/2/19 上午9:33
 */
@Schema
@Data
public class DistributionGoodsAddRequest extends BaseRequest {

    private static final long serialVersionUID = -3070598432384769908L;

    /**
     * 批量添加分销商品
     */
    @Schema(description = "批量skuIds")
    @NotNull
    private List<DistributionGoodsInfoModifyDTO> distributionGoodsInfoModifyDTOS;

    /**
     * 分销商品审核状态 0:普通商品 1:待审核 2:已审核通过 3:审核不通过 4:禁止分销
     */
    @Schema(description = "分销商品审核状态")
    @NotNull
    private DistributionGoodsAudit distributionGoodsAudit;
}
