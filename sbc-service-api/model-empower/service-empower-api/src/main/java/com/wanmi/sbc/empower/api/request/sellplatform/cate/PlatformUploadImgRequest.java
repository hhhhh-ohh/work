package com.wanmi.sbc.empower.api.request.sellplatform.cate;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  上传文件
 * @author  wur
 * @date: 2022/4/1 14:26
 * resp_type=1
 * upload_type=1
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformUploadImgRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "图片地址")
    private String imgUrl;

}
