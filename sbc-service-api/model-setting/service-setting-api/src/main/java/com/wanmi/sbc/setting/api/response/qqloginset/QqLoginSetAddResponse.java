package com.wanmi.sbc.setting.api.response.qqloginset;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.QqLoginSetVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>qq登录信息新增结果</p>
 * @author lq
 * @date 2019-11-05 16:11:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QqLoginSetAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的qq登录信息信息
     */
    @Schema(description = "已新增的qq登录信息信息")
    private QqLoginSetVO qqLoginSetVO;
}
