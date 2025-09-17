package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.enums.PayWay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>对账汇总返回结构</p>
 * Created by of628-wenzhi on 2017-12-11-下午3:01.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountGatherVO extends BasicResponse {

    private static final long serialVersionUID = 4096901530743725896L;
    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private String payWay;

    /**
     * 汇总金额，格式："￥#,###.00"
     */
    @Schema(description = "汇总金额")
    private String sumAmount;

    /**
     * 百分比，格式："##.00%"
     */
    @Schema(description = "百分比")
    private String percentage;
}
