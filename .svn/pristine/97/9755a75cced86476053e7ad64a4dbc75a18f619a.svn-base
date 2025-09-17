package com.wanmi.sbc.order.api.request.orderinvoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 14:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderInvoiceModifyStateRequest extends BaseRequest implements Serializable {

    /**
     * 开票id列表
     */
    @Schema(description = "开票id列表")
    private List<String> orderInvoiceIds;


    @Override
    public void checkParam() {
        if (CollectionUtils.isEmpty(orderInvoiceIds)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        for (String orderInvoiceId : orderInvoiceIds) {
            if (StringUtils.isBlank(orderInvoiceId)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}

