package com.wanmi.sbc.elastic.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.elastic.bean.vo.customer.EsCustomerDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 会员详情查询参数
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsCustomerDetailPageResponse extends BasicResponse {


    /**
     * 会员分页
     */
    @Schema(description = "会员分页")
    private List<EsCustomerDetailVO> detailResponseList;

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
