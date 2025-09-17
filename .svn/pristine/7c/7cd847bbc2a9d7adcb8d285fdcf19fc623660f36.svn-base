package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 根据积分商品编号查询商品信息请求对象
 * @author yinxianzhi
 * @dateTime 2019/5/24 上午9:40
 */
@Schema
@Data
public class GoodsViewByPointsGoodsIdRequest extends BaseRequest {

    private static final long serialVersionUID = 3494892447179733806L;

    @Schema(description = "积分商品Id")
    @NotBlank
    private String pointsGoodsId;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    private Boolean showLabelFlag;

    @Schema(description = "当showLabelFlag=true时，true:返回开启状态的标签，false或null:所有标签")
    private Boolean showSiteLabelFlag;

}
