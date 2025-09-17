package com.wanmi.sbc.empower.bean.vo.sellplatform.promoter;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
 * @description  WxChannelsPromoterInfoVO 推广员详情
 * @author  wur
 * @date: 2022/4/13 15:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PlatformPromoterInfoVO implements Serializable {

    /**
     * 推广员视频号昵称
     */
    @Schema(description = "推广员视频号昵称")
    private String finder_nickname;

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
}
