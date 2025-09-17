package com.wanmi.sbc.setting.api.response.businessconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.BusinessConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>招商页设置列表结果</p>
 * @author lq
 * @date 2019-11-05 16:09:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessConfigListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 招商页设置列表结果
     */
    @Schema(description = "招商页设置列表结果")
    private List<BusinessConfigVO> businessConfigVOList;
}
