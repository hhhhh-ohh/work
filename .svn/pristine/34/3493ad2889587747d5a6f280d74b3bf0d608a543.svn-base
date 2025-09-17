package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）礼品卡批次信息response</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBatchByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 礼品卡批次信息
     */
    @Schema(description = "礼品卡批次信息")
    private GiftCardBatchVO giftCardBatchVO;
}
