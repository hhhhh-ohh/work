package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.bean.vo.LogisticsVO;
import com.wanmi.sbc.order.bean.vo.ShippingItemVO;
import com.wanmi.sbc.order.bean.vo.TradeDeliverVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/4/19.
 */
@Data
@Schema
public class TradeDeliverSmallRequestDTO extends BaseQueryRequest {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotNull(message = "订单号不能为空")
    private String tid;


    /**
     * 物流单号
     */
    @Schema(description = "物流单号")
    @NotBlank(message = "物流单号不能为空")
    private String deliverNo;


    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    @NotBlank(message = "发货时间不能为空")
    @Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$")
    private String deliverTime;

    /**
     * @return
     */
    public TradeDeliverVO toTradeDevlier(ExpressCompanyVO expressCompany) {
        LogisticsVO logistics = null;
        if (Objects.nonNull(expressCompany)) {
            logistics = LogisticsVO.builder()
                    .logisticCompanyId(expressCompany.getExpressCompanyId().toString())
                    .logisticCompanyName(expressCompany.getExpressName())
                    .logisticNo(deliverNo)
                    .logisticStandardCode(expressCompany.getExpressCode())
                    .build();

            logistics.setLogisticNo(deliverNo);
        }
        TradeDeliverVO tradeDeliver = new TradeDeliverVO();
        tradeDeliver.setLogistics(logistics);
        tradeDeliver.setDeliverTime(DateUtil.parseDay(deliverTime));
        return tradeDeliver;
    }

}
