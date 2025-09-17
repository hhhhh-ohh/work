package com.wanmi.sbc.setting.api.response.push;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.AppPushConfigVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>查询任意（包含已删除）消息推送信息response</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushConfigDetailResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 消息推送信息
     */
    @Schema(description = "消息推送信息")
    private AppPushConfigVO appPushConfigVO;
}
