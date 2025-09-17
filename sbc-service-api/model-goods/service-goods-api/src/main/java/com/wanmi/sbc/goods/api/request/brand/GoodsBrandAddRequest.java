package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.goods.bean.dto.GoodsBrandDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品牌新增请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsBrandAddRequest extends GoodsBrandDTO {
    private static final long serialVersionUID = 9024773272281507344L;
}
