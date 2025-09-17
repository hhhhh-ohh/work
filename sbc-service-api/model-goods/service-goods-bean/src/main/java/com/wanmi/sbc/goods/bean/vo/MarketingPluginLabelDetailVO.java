package com.wanmi.sbc.goods.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhanggaolei
 * @className MarketingPluginLabelDetailVO
 * @description TODO
 * @date 2021/6/26 10:34
 **/
@Data
public class MarketingPluginLabelDetailVO extends MarketingPluginLabelVO{

    /**
     * 营销明细
     */
    @Schema(description = "营销明细")
    private Object detail;

    @Schema(description = "活动类型 0: 满金额 1：满数量")
    private Integer subType;

    public <T> T getDetail(){
        return (T) detail;
    }
}
