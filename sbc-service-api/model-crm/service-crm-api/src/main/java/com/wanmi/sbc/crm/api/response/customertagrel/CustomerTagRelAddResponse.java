package com.wanmi.sbc.crm.api.response.customertagrel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomerTagRelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员标签关联新增结果</p>
 * @author dyt
 * @date 2019-11-12 14:49:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagRelAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的会员标签关联信息
     */
    @Schema(description = "已新增的会员标签关联信息")
    private CustomerTagRelVO customerTagRelVO;
}
