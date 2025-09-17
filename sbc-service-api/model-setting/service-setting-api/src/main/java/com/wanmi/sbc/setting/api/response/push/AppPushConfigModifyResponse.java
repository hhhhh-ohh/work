package com.wanmi.sbc.setting.api.response.push;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.AppPushConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>消息推送修改结果</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushConfigModifyResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 已修改的消息推送信息
     */
    @Schema(description = "已修改的消息推送信息")
    private AppPushConfigVO appPushConfigVO;
}
