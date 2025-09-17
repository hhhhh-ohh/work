package com.wanmi.sbc.purchase.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PurchaseGoodsQueryRequest extends BaseRequest {

    /**
     * 商品信息
     */
    @Schema(description = "商品ids")
    private List<String> goodsInfoIds;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    @Override
    public void checkParam(){
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
