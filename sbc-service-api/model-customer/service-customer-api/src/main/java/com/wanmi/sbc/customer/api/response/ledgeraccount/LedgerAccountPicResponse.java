package com.wanmi.sbc.customer.api.response.ledgeraccount;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerAccountPicResponse
 * @description
 * @date 2022/10/20 2:24 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountPicResponse implements Serializable {
    private static final long serialVersionUID = 2466919578667409543L;

    /**
     * 法人身份证正面 图片内容
     */
    @Schema(description = "法人身份证正面 图片内容")
    private byte[] idCardFrontPicContent;

    /**
     * 法人身份证背面 图片内容
     */
    @Schema(description = "法人身份证背面 图片内容")
    private byte[] idCardBackPicContent;

    /**
     * 银行卡 图片内容
     */
    @Schema(description = "银行卡 图片内容")
    private byte[] bankCardPicContent;

    /**
     * 营业执照 图片内容
     */
    @Schema(description = "营业执照 图片内容")
    private byte[] businessPicContent;

    /**
     * 商户门头照 图片内容
     */
    @Schema(description = "商户门头照 图片内容")
    private byte[] merchantPicContent;

    /**
     * 商户内部照 图片内容
     */
    @Schema(description = "商户内部照 图片内容")
    private byte[] shopinnerPicContent;
}
