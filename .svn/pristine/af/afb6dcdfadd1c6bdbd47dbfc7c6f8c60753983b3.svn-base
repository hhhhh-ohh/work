package com.wanmi.sbc.message.api.response.minimsgrecord;

import com.wanmi.sbc.message.bean.vo.MiniMsgAuthorizationRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）小程序订阅消息配置表信息response</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgRecordByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 小程序订阅消息配置表信息
     */
    @Schema(description = "小程序订阅消息配置表信息")
    private MiniMsgAuthorizationRecordVO miniMsgAuthorizationRecordVO;
}
