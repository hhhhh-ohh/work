package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.GiftCardTransBusinessItemVO;
import com.wanmi.sbc.marketing.bean.vo.GiftCardTransBusinessVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wur
 * @className UserGiftCardUseResponse
 * @description 会员礼品卡抵扣
 * @date 2022/12/10 5:56 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardUseResponse implements Serializable {
    private static final long serialVersionUID = 5058071220683992806L;

    /**
     * 会员可用礼品卡Id
     */
    @Schema(description = "会员可用礼品卡Id")
    private Long userGiftCardId;

    /**
     * 礼品卡卡号
     */
    @Schema(description = "礼品卡卡号")
    private String giftCardNo;

    /**
     * 实际抵扣金额
     */
    @Schema(description = "实际抵扣金额")
    private BigDecimal usePrice;

    /**
     * 交易后明细   返回真实抵扣金额
     */
    @Schema(description = "交易后明细   返回真实抵扣金额")
    private List<GiftCardTransBusinessVO> transBusinessVOList;


}
