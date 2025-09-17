package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.bean.vo.AppointmentSaleSimplifyVO;
import com.wanmi.sbc.marketing.bean.vo.BookingSaleSimplifyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品索引SKU查询结果
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsGoodsInfoSimpleResponse extends BasicResponse {

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage<EsGoodsInfoVO> esGoodsInfoPage = new MicroServicePage<>(new ArrayList<>());

}
