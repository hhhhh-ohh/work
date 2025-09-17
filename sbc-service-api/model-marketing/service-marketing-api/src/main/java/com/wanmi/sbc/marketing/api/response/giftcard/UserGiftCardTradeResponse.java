package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvzhenwei
 * @className UserGiftCardTradeResponse
 * @description 会员礼品卡详情
 * @date 2022/12/10 5:56 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardTradeResponse implements Serializable {
    private static final long serialVersionUID = 5058071220683992806L;

    /**
     * 会员可用礼品卡详情信息
     */
    @Schema(description = "可用礼品卡详情信息")
    private List<UserGiftCardInfoVO> validGiftCardInfoVO;

    /**
     * 可用礼品卡数量
     */
    @Schema(description = "可用礼品卡数量")
    private Long validNum;


    /**
     * 会员不可用礼品卡详情信息
     */
    @Schema(description = "会员礼品卡详情信息")
    private List<UserGiftCardInfoVO> invalidGiftCardInfoVO;

    /**
     * 不可用礼品卡数量
     */
    @Schema(description = "不可用礼品卡数量")
    private Long invalidNum;
}
