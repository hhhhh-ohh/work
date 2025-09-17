package com.wanmi.sbc.xsite;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class ImgRequest extends BaseRequest {

    @Schema(description = "分类")
    private String cateId;

    @Schema(description = "图片列表")
    private List<ImageForms> imageForms;

    @Schema(description = "店铺id")
    private Long storeId;
}
