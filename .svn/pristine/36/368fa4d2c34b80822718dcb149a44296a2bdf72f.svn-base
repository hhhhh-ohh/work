package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 会员信息响应
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailListByPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 会员信息列表
     */
    @Schema(description = "会员信息列表")
    private List<CustomerDetailVO> detailResponseList;
}
