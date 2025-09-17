package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 会员信息响应
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
public class CustomerDetailPageVO extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 会员分页
     */
    @Schema(description = "会员分页")
    private List<CustomerDetailForPageVO> detailResponseList;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long total;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Integer currentPage;
}
