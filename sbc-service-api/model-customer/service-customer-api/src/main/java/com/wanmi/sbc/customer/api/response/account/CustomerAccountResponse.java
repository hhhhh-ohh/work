package com.wanmi.sbc.customer.api.response.account;

import com.wanmi.sbc.customer.bean.vo.CustomerAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: wanggang
 * @CreateDate: 2018/9/11 11:07
 * @Version: 1.0
 */
@Schema
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerAccountResponse extends CustomerAccountVO implements Serializable{

    private static final long serialVersionUID = 1L;
}
