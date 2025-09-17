package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）礼品卡详情信息response</p>
 * @author 马连峰
 * @date 2022-12-09 14:08:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 礼品卡详情信息
     */
    @Schema(description = "礼品卡详情信息")
    private GiftCardDetailVO giftCardDetailVO;
}
