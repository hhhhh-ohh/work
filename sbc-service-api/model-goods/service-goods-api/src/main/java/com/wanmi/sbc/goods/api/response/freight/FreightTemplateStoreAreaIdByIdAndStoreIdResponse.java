package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据运费模板id和店铺id获取区域id响应
 * Created by daiyitian on 2018/5/3.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateStoreAreaIdByIdAndStoreIdResponse extends BasicResponse {

    private static final long serialVersionUID = -5877557149459733450L;

    /**
     * 区域ids
     */
    @Schema(description = "区域ids")
    private List<Long> areaIds;
}
