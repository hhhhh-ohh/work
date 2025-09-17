package com.wanmi.sbc.customer.api.response.level;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 客户等级分页
 * @Author: daiyitian
 * @Date: Created In 上午11:38 2017/11/14
 * @Description: 公司信息Response
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLevelByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级列表
     */
    @Schema(description = "客户等级列表")
    private List<CustomerLevelVO> customerLevelVOList;

}
