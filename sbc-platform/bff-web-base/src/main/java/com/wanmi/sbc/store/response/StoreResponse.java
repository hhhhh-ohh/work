package com.wanmi.sbc.store.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.api.response.store.StoreBaseInfoResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺列表返回
 * Created by daiyitian on 2017/7/22.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse extends BasicResponse {

    /**
     * 分页信息
     */
    @Schema(description = "分页信息")
    private Page<StoreBaseInfoResponse> storePage = new PageImpl<>(new ArrayList<>());

    /**
     * 商品SKU列表
     */
    @Schema(description = "商品SKU列表")
    private List<GoodsInfoVO> goodsInfoList = new ArrayList<>();

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices = new ArrayList<>();

}
