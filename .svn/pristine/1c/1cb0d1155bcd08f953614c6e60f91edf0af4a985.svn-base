package com.wanmi.sbc.customer.api.response.storeevaluatenum;

import com.wanmi.sbc.customer.bean.vo.StoreEvaluateNumVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: jiaojiao
 * @Date: 2019/3/19 14:56
 * @Description:
 */
@Schema
@Data
public class StoreEvaluateNumResponse
{
    @Schema(description = "各项评分列表")
    private List<StoreEvaluateNumVO> storeEvaluateNumVOList;

    @Schema(description = "评价人数")
    private Long evaluateCount;

    @Schema(description = "综合评分")
    private BigDecimal compositeScore;


}
