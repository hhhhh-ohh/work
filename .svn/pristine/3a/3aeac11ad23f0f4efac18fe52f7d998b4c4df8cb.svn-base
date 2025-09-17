package com.wanmi.sbc.goods.api.response.brand;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 品牌分页查询响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandPageResponse extends BasicResponse {

    private static final long serialVersionUID = 1240446635363986741L;

    /**
     * 品牌分页列表
     */
    @Schema(description = "品牌分页列表")
    private MicroServicePage<GoodsBrandVO> goodsBrandPage;
}
