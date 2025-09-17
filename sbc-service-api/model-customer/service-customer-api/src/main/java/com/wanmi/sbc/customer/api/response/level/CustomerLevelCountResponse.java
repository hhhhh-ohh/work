package com.wanmi.sbc.customer.api.response.level;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 客户等级统计响应
 * @Author: daiyitian
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 公司信息Response
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLevelCountResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级数量
     */
    @Schema(description = "客户等级数量")
    private long count;
}
