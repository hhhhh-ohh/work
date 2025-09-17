package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @className GoodsColumnShowVO
 * @description TODO
 * @author 黄昭
 * @date 2021/9/16 10:27
 **/
@Data
@Schema
public class GoodsColumnShowVO extends BasicResponse {

    private static final long serialVersionUID = -8985581780801296552L;

    @Schema(description = "类型")
    private String configType;

    @Schema(description = "状态")
    private Integer status;
}
