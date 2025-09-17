package com.wanmi.sbc.goods.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.*;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className GoodsInfoListResponse
 * @description TODO
 * @date 2021/8/18 5:40 下午
 **/
@Data
public class GoodsInfoListResponse extends BasicResponse {

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage goodsInfoPage = new MicroServicePage<>(new ArrayList<>());

}
