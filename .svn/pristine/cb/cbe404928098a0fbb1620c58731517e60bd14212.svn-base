package com.wanmi.sbc.empower.api.response.sellplatform.cate;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsUploadImgResponse
 * @description TODO
 * @date 2022/4/6 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformUploadImgResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 临时链接， 临时链接会在微信侧转为永久链接
     */
    @Schema(description = "临时链接， 临时链接会在微信侧转为永久链接")
    private String temp_img_url;

    /**
     * 类目名称
     */
    @Schema(description = "media_id")
    private String media_id;

}