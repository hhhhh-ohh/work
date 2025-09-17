package com.wanmi.sbc.crm.api.response.customertagrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomerTagRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员标签关联列表结果</p>
 * @author dyt
 * @date 2019-11-12 14:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagRelListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员标签关联列表结果
     */
    @Schema(description = "会员标签关联列表结果")
    private List<CustomerTagRelVO> customerTagRelVOList;
}
