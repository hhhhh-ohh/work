package com.wanmi.sbc.marketing.api.response.countprice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
*
 * @description   商品算价响应
 * @author  wur
 * @date: 2022/2/23 16:31
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeCountPricePluginResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 算价后明细
     */
    @Schema(description = "算价后明细")
    private List<CountPriceItemVO> countPriceVOList;
}
