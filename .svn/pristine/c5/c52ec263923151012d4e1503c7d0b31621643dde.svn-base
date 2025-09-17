package com.wanmi.sbc.crm.api.response.customertag;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.CustomerTagVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员标签列表结果</p>
 * @author zhanglingke
 * @date 2019-10-14 11:19:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员标签列表结果
     */
    @Schema(description = "会员标签列表结果")
    private List<CustomerTagVO> customerTagVOList;
}
