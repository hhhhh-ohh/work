package com.wanmi.sbc.order.api.request.trade;/**
 * @author 黄昭
 * @create 2021/9/10 15:56
 */

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @className WriteOffCodeRequest
 * @description
 * @author 黄昭
 * @date 2021/9/10 15:56
 **/
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteOffCodeRequest extends BaseRequest implements Serializable{
    private static final long serialVersionUID = -6808821564739660651L;

    @Schema(description = "核销码")
    private String writeOffCode;

    @Schema(description = "订单号")
    private String tid;

    @Schema(description = "当前登录人归属自提点信息")
    private List<Long> pickupIds;

    @Schema(description = "当前登录人信息")
    private Operator operator;



    @Override
    public void checkParam() {
        if (StringUtils.isBlank(writeOffCode) && StringUtils.isBlank(tid)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (StringUtils.isNotBlank(writeOffCode) && StringUtils.isNotBlank(tid)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
