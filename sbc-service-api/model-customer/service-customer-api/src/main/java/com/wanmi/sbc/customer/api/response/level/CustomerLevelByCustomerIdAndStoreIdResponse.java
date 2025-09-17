package com.wanmi.sbc.customer.api.response.level;

import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户等级分页
 * @Author: daiyitian
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 公司信息Response
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelByCustomerIdAndStoreIdResponse extends CommonLevelVO implements Serializable {

    private static final long serialVersionUID = 1L;

}
