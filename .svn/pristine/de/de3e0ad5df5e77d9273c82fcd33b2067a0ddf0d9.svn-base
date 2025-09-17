package com.wanmi.sbc.message.api.response.minimsgactivitysetting;

import com.wanmi.sbc.message.bean.vo.MiniMsgActivitySettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）小程序订阅消息配置表信息response</p>
 * @author xufeng
 * @date 2022-08-11 16:16:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgActivitySettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序新增活动消息配置
     */
    @Schema(description = "小程序新增活动消息配置")
    private MiniMsgActivitySettingVO miniMsgActivitySettingVO;
}
