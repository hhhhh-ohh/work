package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 会员详情查询参数
 * Created by baijz on 2017/4/19.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailListByPageFordrRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;


    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 是否忽略会员状态
     */
    @Schema(description = "是否忽略会员状态")
    private Boolean isIgnoreStatus;
}
