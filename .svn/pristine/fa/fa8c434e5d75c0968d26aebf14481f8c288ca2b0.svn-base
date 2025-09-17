package com.wanmi.sbc.elastic.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;

import com.wanmi.sbc.customer.bean.vo.StoreEvaluateSumVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author houshuai
 * 店铺评价分页
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsStoreEvaluateSumPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺评价分页结果
     */
    @Schema(description = "店铺评价分页结果")
    private MicroServicePage<StoreEvaluateSumVO> storeEvaluateSumVOPage;
}
