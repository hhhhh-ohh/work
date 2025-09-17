package com.wanmi.sbc.setting.api.response.umengpushconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.UmengPushConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>友盟push接口配置新增结果</p>
 * @author bob
 * @date 2020-01-07 10:34:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmengPushConfigAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的友盟push接口配置信息
     */
    @Schema(description = "已新增的友盟push接口配置信息")
    private UmengPushConfigVO umengPushConfigVO;
}
