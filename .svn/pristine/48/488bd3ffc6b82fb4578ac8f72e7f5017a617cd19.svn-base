package com.wanmi.sbc.setting.api.response.baseconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.BaseConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>基本设置列表结果</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 基本设置列表结果
     */
    @Schema(description = "基本设置列表结果")
    private List<BaseConfigVO> baseConfigVOList;
}
