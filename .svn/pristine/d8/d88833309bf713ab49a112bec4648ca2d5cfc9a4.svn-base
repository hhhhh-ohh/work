package com.wanmi.sbc.goods.api.request.goodsrestrictedsale;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsRestrictedBatchValidateSimpleRequest
 * @description TODO
 * @date 2021/6/15 10:08
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedBatchValidateSimpleRequest extends BaseRequest {
    List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS;

    CustomerSimplifyVO customerVO;

    Long storeId;
}
