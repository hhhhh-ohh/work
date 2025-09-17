package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午2:21 2019/3/1
 * @Description:
 */
@Schema
@Data
public class DistributorGoodsInfoVerifyRequest extends BaseRequest {

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private String distributorId;

    /**
     * 单品id列表
     */
    @Schema(description = "单品id列表")
    private List<String> goodsInfoIds;

}
