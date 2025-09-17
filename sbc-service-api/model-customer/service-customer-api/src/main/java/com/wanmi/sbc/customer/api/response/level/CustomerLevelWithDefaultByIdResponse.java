package com.wanmi.sbc.customer.api.response.level;

import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 默认客户等级查询数据
 * @Author: daiyitian
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 公司信息Response
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLevelWithDefaultByIdResponse extends CustomerLevelVO implements Serializable {

    private static final long serialVersionUID = 1L;

}
