package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商家关联平台客户参数
 * Created by xufeng on 2021/8/18.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsCustomerRelatedAddRequest extends BaseRequest {

    private static final long serialVersionUID = -1281379836937123214L;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

}
