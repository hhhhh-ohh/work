package com.wanmi.sbc.customer.api.response.email;

import com.wanmi.sbc.customer.bean.vo.CustomerEmailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 邮箱服务器设置
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerEmailAddResponse extends CustomerEmailVO {

    private static final long serialVersionUID = 1L;

}
