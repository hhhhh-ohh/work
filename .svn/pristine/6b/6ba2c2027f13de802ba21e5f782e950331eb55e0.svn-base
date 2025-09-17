package com.wanmi.sbc.customer.api.response.email;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerEmailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 获取邮箱列表请求类
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoDeleteCustomerEmailListByCustomerIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱列表
     */
    @Schema(description = "邮箱列表")
    private List<CustomerEmailVO> customerEmails;
}
