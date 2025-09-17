package com.wanmi.sbc.customer.api.response.account;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wanggang
 * @CreateDate: 2018/9/11 11:07
 * @Version: 1.0
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountListResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "会员银行账号")
    private List<CustomerAccountVO> customerAccountVOList;
}
