package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*
 * @description 会员礼品卡列表
 * @author  wur
 * @date: 2022/12/12 15:03
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardInfoPageResponse implements Serializable {
    private static final long serialVersionUID = 5058071220683992806L;

    /**
     * 分页列表
     */
    @Schema(description = "会员礼品卡列表")
    private MicroServicePage<UserGiftCardInfoVO> userGiftCardInfoVOS;

    @Schema(description = " 未激活礼品卡数量")
    private Long notActiveNum;

    @Schema(description = "不可用数量")
    private Long invalidNum;

    @Schema(description = "可用数量")
    private Long useNum;

    @Schema(description = "礼品卡余额")
    private BigDecimal cardBalance;
}
