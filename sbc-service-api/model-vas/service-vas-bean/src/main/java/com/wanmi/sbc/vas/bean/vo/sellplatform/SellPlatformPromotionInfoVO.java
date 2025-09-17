package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  推广员、分享员信息
 * @author  wur
 * @date: 2022/4/13 11:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class SellPlatformPromotionInfoVO implements Serializable {
    /**
     * 推广员唯一ID
     */
    @Schema(description = "推广员唯一ID")
    private String promoter_id;

    /**
     * 推广员openid
     */
    @Schema(description = "推广员openid")
    private String promoter_openid;

    /**
     * 推广员视频号昵称
     */
    @Schema(description = "推广员视频号昵称")
    private String finder_nickname;

    /**
     * 分享员openid
     */
    @Schema(description = "分享员openid")
    private String sharer_openid;
}
