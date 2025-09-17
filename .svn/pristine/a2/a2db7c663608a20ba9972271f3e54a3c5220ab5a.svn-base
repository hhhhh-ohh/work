package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.CollectPageFreightFlag;
import com.wanmi.sbc.goods.bean.vo.FreightGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectPageInfoResponse extends BasicResponse {

    /**
     * 目标商品总数
     */
    @Schema(description = "目标商品总数")
    private Long totalNum;

    /**
     * 目标商品总金额
     */
    @Schema(description = "目标商品总金额")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Schema(description = "条件")
    private BigDecimal conditionAmount;

    @Schema(description = "freightFlag=1时-差额  freightFlag=2时-已超金额")
    private BigDecimal conditionLack = BigDecimal.ZERO;

    @Schema(description = "差额单位")
    private String conditionUnit;

    @Schema(description = "运费标识 0: 包邮 1：不包邮 2：已超出指定范围")
    private CollectPageFreightFlag freightFlag = CollectPageFreightFlag.FREE;

    @Schema(description = "命中商品信息")
    private List<FreightGoodsInfoVO> freightGoodsInfoVOList;

    @Schema(description = "满足条件2金额 只可以是金额")
    private BigDecimal conditionAmountTow;

    @Schema(description = "差额单位")
    private BigDecimal conditionLackTow;

}
