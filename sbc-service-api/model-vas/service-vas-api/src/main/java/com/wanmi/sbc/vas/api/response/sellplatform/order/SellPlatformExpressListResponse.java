package com.wanmi.sbc.vas.api.response.sellplatform.order;

import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformExpressVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @description SellPlatformQueryOrderResponse
 * @author  wur
 * @date: 2022/4/18 20:00
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellPlatformExpressListResponse implements Serializable {

    private static final long serialVersionUID = 59124042365355768L;

    /**
     *  物流公司列表
     */
    @Schema(description = "物流公司列表")
    private List<SellPlatformExpressVO> expressList;
}
