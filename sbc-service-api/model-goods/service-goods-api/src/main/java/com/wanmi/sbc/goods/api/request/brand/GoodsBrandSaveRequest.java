package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsBrandDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌更新请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
public class GoodsBrandSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -4748585533773368408L;

    /**
     * 商品品牌信息
     */
    @Schema(description = "商品品牌信息")
    private GoodsBrandDTO goodsBrand;
}
