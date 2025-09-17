package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>店铺评价修改</p>
 * @author xufeng
 * @date 2021-02-19 10:17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsStoreEvaluateSumAnswerRequest extends BaseRequest {

    /**
     * 已修改的店铺评价信息
     */
    @Schema(description = "商品评价信息")
    private StoreEvaluateVO storeEvaluateVO;
}