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
 * <p>根据id查询任意（包含已删除）qq登录信息信息response</p>
 * @author lq
 * @date 2019-11-05 16:11:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QqLoginSetByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * qq登录信息信息
     */
    @Schema(description = "qq登录信息信息")
    private QqLoginSetVO qqLoginSetVO;
}
