package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.MiniStoreVO;
import com.wanmi.sbc.goods.bean.vo.FreightCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;
import com.wanmi.sbc.order.bean.vo.GoodsInfoCartSimpleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className GoodsCartListResponse
 * @description TODO
 * @date 2022/1/13 11:15 上午
 **/
@Data
public class GoodsCartListResponse extends BasicResponse {

    @Schema(description = "商品")
    private List<GoodsInfoCartSimpleVO> goodsInfos;

    @Schema(description = "商品所选择的营销")
    private List<GoodsMarketingVO> goodsMarketings;

    @Schema(description = "店铺信息")
    private List<MiniStoreVO> stores;

    @Schema(description = "营销活动详情")
    private Map<String,Object> marketingDetail;

    @Schema(description = "运费模板")
    private List<FreightCartVO> freightCartVOS;

    @Schema(description = "可用积分")
    private Long pointsAvailable;
}
