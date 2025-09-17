package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.UserGiftCardVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvzhenwei
 * @className UserGiftCardInfoResponse
 * @description 会员礼品卡详情
 * @date 2022/12/10 5:56 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardListResponse implements Serializable {
    private static final long serialVersionUID = 5058071220683992806L;

    /**
     * 会员礼品卡详情信息
     */
    @Schema(description = "会员礼品卡详情信息")
    private List<UserGiftCardVO> userGiftCardVO;
}
