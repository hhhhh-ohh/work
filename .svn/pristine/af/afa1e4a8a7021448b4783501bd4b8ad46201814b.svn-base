package com.wanmi.sbc.crm.api.response.customertag;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomerTagVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员标签修改结果</p>
 * @author zhanglingke
 * @date 2019-10-14 11:19:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的会员标签信息
     */
    @Schema(description = "已修改的会员标签信息")
    private CustomerTagVO customerTagVO;
}
