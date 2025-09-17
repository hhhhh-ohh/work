package com.wanmi.sbc.customer.api.response.level;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yang
 * @since 2019/3/5
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelWithRightsByCustomerIdResponse extends CustomerLevelVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户成长值
     */
    private Long customerGrowthValue;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户头像
     */
    private String headImg;

    /**
     * 会员过期时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime membershipExpiredTime;


}
