package com.wanmi.sbc.empower.api.response.apppush;

import com.wanmi.sbc.empower.bean.vo.AppPushSendResultVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>消息推送结果</p>
 * @author 韩伟
 * @date 2021-04-01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushSendResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息推送配置列表结果
     */
    @Schema(description = "消息推送结果列表")
    private List<AppPushSendResultVO> dataList;
}
