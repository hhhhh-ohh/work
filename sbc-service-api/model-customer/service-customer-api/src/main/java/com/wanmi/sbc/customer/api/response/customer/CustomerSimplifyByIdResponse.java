package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerSimplifyByIdResponse extends CustomerSimplifyOrderCommitVO {
    private static final long serialVersionUID = 1L;


}
