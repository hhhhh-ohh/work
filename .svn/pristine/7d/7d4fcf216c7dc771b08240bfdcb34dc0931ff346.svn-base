package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBillVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lvzhenwei
 * @className GiftCardBillPageResponse
 * @description 会员礼品卡使用记录
 * @date 2022/12/12 11:26 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBillPageResponse implements Serializable {
    private static final long serialVersionUID = 2870232016958178235L;

    /**
     * 礼品卡使用记录分页结果
     */
    @Schema(description = "礼品卡使用记录分页结果")
    private MicroServicePage<GiftCardBillVO> giftCardBillVOPage;
}
