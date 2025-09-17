package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author houshuai
 * @date 2020/12/10 10:24
 * @description <p> </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class EsGoodsBrandPageResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌分页列表
     */
    @Schema(description = "品牌分页列表")
    private MicroServicePage<GoodsBrandVO> goodsBrandPage;
}
