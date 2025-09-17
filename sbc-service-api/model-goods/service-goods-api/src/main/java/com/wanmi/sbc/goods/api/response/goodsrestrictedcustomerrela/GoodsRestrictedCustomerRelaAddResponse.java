package com.wanmi.sbc.goods.api.response.goodsrestrictedcustomerrela;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedCustomerRelaVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售配置会员关系表新增结果</p>
 * @author baijz
 * @date 2020-04-08 11:32:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedCustomerRelaAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的限售配置会员关系表信息
     */
    @Schema(description = "已新增的限售配置会员关系表信息")
    private GoodsRestrictedCustomerRelaVO goodsRestrictedCustomerRelaVO;
}
