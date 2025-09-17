package com.wanmi.sbc.marketing.api.request.grouponrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>拼团活动参团信息表新增参数</p>
 * @author groupon
 * @date 2019-05-17 16:17:44
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrouponRecordByCustomerRequest extends BaseRequest {

    /**
     * 拼团活动ID
     */
    @NotNull
    private String grouponActivityId;

    /**
     * 会员ID
     */
    @NotNull
    private String customerId;

    /**
     * sku编号
     */
    private String goodsInfoId;


}