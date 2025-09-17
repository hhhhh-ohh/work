package com.wanmi.sbc.order.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.CountCustomerConsumeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class CountConsumeResponse extends BasicResponse {

    @Schema(description = "统计结果")
    private List<CountCustomerConsumeVO> countCustomerConsumeList;
}
