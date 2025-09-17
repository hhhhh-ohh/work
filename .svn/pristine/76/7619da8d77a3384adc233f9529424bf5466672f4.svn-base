package com.wanmi.sbc.customer.api.response.customersignrecord;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerSignRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>用户签到记录列表结果</p>
 * @author wangtao
 * @date 2019-10-05 16:13:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignRecordListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 用户签到记录列表结果
     */
    @Schema(description = "用户签到记录列表结果")
    private List<CustomerSignRecordVO> customerSignRecordVOList;
}
